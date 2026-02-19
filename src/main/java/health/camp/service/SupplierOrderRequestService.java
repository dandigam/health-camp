package health.camp.service;

import health.camp.dto.supplier.MonthlyOrderTrackingDto;
import health.camp.dto.supplier.SupplierOrderRequestDto;
import health.camp.entity.*;
import health.camp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierOrderRequestService {

    private final SupplierRequestRepository supplierRequestRepository;
    private final SupplierRequestItemRepository supplierRequestItemRepository;
    private final WareHouseRepository wareHouseRepository;
    private final SupplierRepository supplierRepository;
    private final MedicineRepository medicineRepository;
    private final WarehouseInventoryRepository warehouseInventoryRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public SupplierOrderRequestDto createOrderRequest(SupplierOrderRequestDto dto) {
        WareHouse warehouse = wareHouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        // Create the request - default to DRAFT, or use provided status
        SupplierRequest.Status status = SupplierRequest.Status.DRAFT;
        if (dto.getStatus() != null) {
            status = SupplierRequest.Status.valueOf(dto.getStatus());
        }

        SupplierRequest request = SupplierRequest.builder()
                .warehouse(warehouse)
                .supplier(supplier)
                .status(status)
                .items(new ArrayList<>())
                .build();

        SupplierRequest savedRequest = supplierRequestRepository.save(request);

        // Add items
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (SupplierOrderRequestDto.ItemDto itemDto : dto.getItems()) {
                MedicineLookup medicine = medicineRepository.findById(itemDto.getMedicineId())
                        .orElseThrow(() -> new RuntimeException("Medicine not found: " + itemDto.getMedicineId()));

                SupplierRequestItem item = SupplierRequestItem.builder()
                        .request(savedRequest)
                        .medicine(medicine)
                        .requestedQuantity(itemDto.getRequestedQuantity())
                        .receivedQuantity(0)
                        .unitPrice(itemDto.getUnitPrice())
                        .status(SupplierRequestItem.Status.PENDING)
                        .build();

                supplierRequestItemRepository.save(item);
            }
        }

        dto.setId(savedRequest.getRequestId());
        dto.setStatus(savedRequest.getStatus().name());
        return dto;
    }

    @Transactional
    public SupplierOrderRequestDto updateOrderRequest(Long requestId, SupplierOrderRequestDto dto) {
        SupplierRequest request = supplierRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Update status if provided
        if (dto.getStatus() != null) {
            request.setStatus(SupplierRequest.Status.valueOf(dto.getStatus()));
        }

        supplierRequestRepository.save(request);

        // Update items if provided
        if (dto.getItems() != null) {
            for (SupplierOrderRequestDto.ItemDto itemDto : dto.getItems()) {
                if (itemDto.getId() != null) {
                    // Update existing item by ID
                    SupplierRequestItem item = supplierRequestItemRepository.findById(itemDto.getId())
                            .orElseThrow(() -> new RuntimeException("Item not found: " + itemDto.getId()));

                    // If quantity is 0, delete the item
                    if (itemDto.getRequestedQuantity() != null && itemDto.getRequestedQuantity() == 0) {
                        supplierRequestItemRepository.delete(item);
                        continue;
                    }

                    if (itemDto.getRequestedQuantity() != null) {
                        item.setRequestedQuantity(itemDto.getRequestedQuantity());
                    }
                    if (itemDto.getReceivedQuantity() != null && itemDto.getReceivedQuantity() > 0) {
                        // Only add to inventory if not already received
                        boolean alreadyReceived = item.getReceivedQuantity() != null && item.getReceivedQuantity() > 0;
                        
                        // Set received quantity in order item
                        item.setReceivedQuantity(itemDto.getReceivedQuantity());

                        // Add to warehouse inventory only once (first time received)
                        if (!alreadyReceived) {
                            addToWarehouseInventory(request.getWarehouse(), item.getMedicine(), itemDto.getReceivedQuantity());
                        }
                    }
                    if (itemDto.getUnitPrice() != null) {
                        item.setUnitPrice(itemDto.getUnitPrice());
                    }
                    if (itemDto.getStatus() != null) {
                        item.setStatus(SupplierRequestItem.Status.valueOf(itemDto.getStatus()));
                    }

                    supplierRequestItemRepository.save(item);
                } else {
                    // Check if medicine already exists in this request
                    MedicineLookup medicine = medicineRepository.findById(itemDto.getMedicineId())
                            .orElseThrow(() -> new RuntimeException("Medicine not found: " + itemDto.getMedicineId()));

                    Optional<SupplierRequestItem> existingItem = supplierRequestItemRepository
                            .findByRequestAndMedicine(request, medicine);

                    if (existingItem.isPresent()) {
                        SupplierRequestItem item = existingItem.get();
                        
                        // If quantity is 0, delete the item
                        if (itemDto.getRequestedQuantity() != null && itemDto.getRequestedQuantity() == 0) {
                            supplierRequestItemRepository.delete(item);
                            continue;
                        }

                        // Update existing item quantity
                        if (itemDto.getRequestedQuantity() != null) {
                            item.setRequestedQuantity(itemDto.getRequestedQuantity());
                        }
                        if (itemDto.getUnitPrice() != null) {
                            item.setUnitPrice(itemDto.getUnitPrice());
                        }
                        supplierRequestItemRepository.save(item);
                    } else {
                        // Skip if quantity is 0 for new item
                        if (itemDto.getRequestedQuantity() == null || itemDto.getRequestedQuantity() == 0) {
                            continue;
                        }
                        
                        // Add new item
                        SupplierRequestItem newItem = SupplierRequestItem.builder()
                                .request(request)
                                .medicine(medicine)
                                .requestedQuantity(itemDto.getRequestedQuantity())
                                .receivedQuantity(0)
                                .unitPrice(itemDto.getUnitPrice())
                                .status(SupplierRequestItem.Status.PENDING)
                                .build();

                        supplierRequestItemRepository.save(newItem);
                    }
                }
            }
        }

        return getOrderRequest(requestId);
    }

    public SupplierOrderRequestDto getOrderRequest(Long requestId) {
        SupplierRequest request = supplierRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        return mapToDto(request);
    }

    public List<SupplierOrderRequestDto> getOrderRequestsByWarehouse(Long warehouseId) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return supplierRequestRepository.findByWarehouse(warehouse).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<SupplierOrderRequestDto> getOrderRequestsByWarehouseAndStatus(Long warehouseId, String status) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return supplierRequestRepository.findByWarehouseAndStatus(warehouse, SupplierRequest.Status.valueOf(status))
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteOrderRequest(Long requestId) {
        SupplierRequest request = supplierRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Items will be deleted due to cascade
        supplierRequestRepository.delete(request);
    }

    @Transactional
    public void removeItemFromRequest(Long requestId, Long itemId) {
        SupplierRequestItem item = supplierRequestItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getRequest().getRequestId().equals(requestId)) {
            throw new RuntimeException("Item does not belong to this request");
        }

        supplierRequestItemRepository.delete(item);
    }

    @Transactional
    public SupplierOrderRequestDto updateRequestStatus(Long requestId, String status) {
        SupplierRequest request = supplierRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(SupplierRequest.Status.valueOf(status));
        supplierRequestRepository.save(request);

        return mapToDto(request);
    }

    public List<MonthlyOrderTrackingDto> getMonthlyOrderTracking(Long warehouseId) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        List<SupplierRequest> allOrders = supplierRequestRepository.findByWarehouse(warehouse);

        // Group orders by year-month
        Map<String, List<SupplierRequest>> groupedByMonth = new LinkedHashMap<>();
        
        for (SupplierRequest order : allOrders) {
            if (order.getCreatedAt() != null) {
                String key = order.getCreatedAt().getYear() + "-" + 
                             String.format("%02d", order.getCreatedAt().getMonthValue());
                groupedByMonth.computeIfAbsent(key, k -> new ArrayList<>()).add(order);
            }
        }

        // Convert to DTOs
        List<MonthlyOrderTrackingDto> result = new ArrayList<>();
        for (Map.Entry<String, List<SupplierRequest>> entry : groupedByMonth.entrySet()) {
            String[] parts = entry.getKey().split("-");
            int year = Integer.parseInt(parts[0]);
            int monthNum = Integer.parseInt(parts[1]);
            
            String monthName = java.time.Month.of(monthNum).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String monthDisplay = monthName + " " + year;

            List<SupplierOrderRequestDto> orderDtos = entry.getValue().stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            result.add(MonthlyOrderTrackingDto.builder()
                    .month(monthDisplay)
                    .year(year)
                    .monthNumber(monthNum)
                    .totalOrders(orderDtos.size())
                    .orders(orderDtos)
                    .build());
        }

        // Sort by year and month descending (most recent first)
        result.sort((a, b) -> {
            if (a.getYear() != b.getYear()) {
                return b.getYear() - a.getYear();
            }
            return b.getMonthNumber() - a.getMonthNumber();
        });

        return result;
    }

    private SupplierOrderRequestDto mapToDto(SupplierRequest request) {
        WareHouse warehouse = request.getWarehouse();
        List<SupplierOrderRequestDto.ItemDto> itemDtos = supplierRequestItemRepository.findByRequest(request)
                .stream()
                .map(item -> {
                    // Get current inventory quantity for this medicine
                    Integer currentQty = warehouseInventoryRepository
                            .findByWarehouseAndMedicine(warehouse, item.getMedicine())
                            .map(inv -> inv.getTotalQty())
                            .orElse(0);
                    
                    return SupplierOrderRequestDto.ItemDto.builder()
                            .id(item.getRequestItemId())
                            .medicineId(item.getMedicine().getMedicineId())
                            .medicineName(item.getMedicine().getName())
                            .medicineType(item.getMedicine().getType())
                            .currentQty(currentQty)
                            .requestedQuantity(item.getRequestedQuantity())
                            .receivedQuantity(item.getReceivedQuantity())
                            .unitPrice(item.getUnitPrice())
                            .status(item.getStatus() != null ? item.getStatus().name() : null)
                            .build();
                })
                .collect(Collectors.toList());

        return SupplierOrderRequestDto.builder()
                .id(request.getRequestId())
                .warehouseId(request.getWarehouse().getId())
                .warehouseName(request.getWarehouse().getName())
                .supplierId(request.getSupplier().getSupplierId())
                .supplierName(request.getSupplier().getName())
                .status(request.getStatus().name())
                .createdAt(request.getCreatedAt() != null ? request.getCreatedAt().format(DATE_FORMATTER) : null)
                .updatedAt(request.getUpdatedAt() != null ? request.getUpdatedAt().format(DATE_FORMATTER) : null)
                .itemCount(itemDtos.size())
                .items(itemDtos)
                .build();
    }

    /**
     * Add received quantity to warehouse inventory
     */
    private void addToWarehouseInventory(WareHouse warehouse, MedicineLookup medicine, int qty) {
        Optional<WarehouseInventory> existingInventory = warehouseInventoryRepository
                .findByWarehouseAndMedicine(warehouse, medicine);

        if (existingInventory.isPresent()) {
            // Update existing inventory
            WarehouseInventory inventory = existingInventory.get();
            inventory.setTotalQty(inventory.getTotalQty() + qty);
            warehouseInventoryRepository.save(inventory);
        } else {
            // Create new inventory entry
            WarehouseInventory inventory = WarehouseInventory.builder()
                    .warehouse(warehouse)
                    .medicine(medicine)
                    .totalQty(qty)
                    .minimumQty(0)
                    .build();
            warehouseInventoryRepository.save(inventory);
        }
    }
}
