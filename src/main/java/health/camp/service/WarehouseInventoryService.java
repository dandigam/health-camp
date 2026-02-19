package health.camp.service;

import health.camp.dto.inventory.WarehouseInventoryDto;
import health.camp.entity.MedicineLookup;
import health.camp.entity.WareHouse;
import health.camp.entity.WarehouseInventory;
import health.camp.repository.MedicineRepository;
import health.camp.repository.WareHouseRepository;
import health.camp.repository.WarehouseInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseInventoryService {

    private final WarehouseInventoryRepository inventoryRepository;
    private final WareHouseRepository wareHouseRepository;
    private final MedicineRepository medicineRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Add stock to inventory (when received from supplier)
     */
    @Transactional
    public WarehouseInventoryDto addStock(Long warehouseId, Long medicineId, Integer qty) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        MedicineLookup medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        Optional<WarehouseInventory> existingInventory = inventoryRepository
                .findByWarehouseAndMedicine(warehouse, medicine);

        WarehouseInventory inventory;
        if (existingInventory.isPresent()) {
            // Update existing inventory
            inventory = existingInventory.get();
            inventory.setTotalQty(inventory.getTotalQty() + qty);
        } else {
            // Create new inventory entry
            inventory = WarehouseInventory.builder()
                    .warehouse(warehouse)
                    .medicine(medicine)
                    .totalQty(qty)
                    .minimumQty(0)
                    .build();
        }

        inventoryRepository.save(inventory);
        return mapToDto(inventory);
    }

    /**
     * Add multiple items to inventory at once
     */
    @Transactional
    public List<WarehouseInventoryDto> addBulkStock(Long warehouseId, WarehouseInventoryDto dto) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return dto.getItems().stream().map(item -> {
            MedicineLookup medicine = medicineRepository.findById(item.getMedicineId())
                    .orElseThrow(() -> new RuntimeException("Medicine not found: " + item.getMedicineId()));

            Optional<WarehouseInventory> existingInventory = inventoryRepository
                    .findByWarehouseAndMedicine(warehouse, medicine);

            WarehouseInventory inventory;
            if (existingInventory.isPresent()) {
                inventory = existingInventory.get();
                inventory.setTotalQty(inventory.getTotalQty() + item.getQty());
                if (item.getMinimumQty() != null) {
                    inventory.setMinimumQty(item.getMinimumQty());
                }
            } else {
                inventory = WarehouseInventory.builder()
                        .warehouse(warehouse)
                        .medicine(medicine)
                        .totalQty(item.getQty())
                        .minimumQty(item.getMinimumQty() != null ? item.getMinimumQty() : 0)
                        .build();
            }

            inventoryRepository.save(inventory);
            return mapToDto(inventory);
        }).collect(Collectors.toList());
    }

    /**
     * Reduce stock from inventory (when sold or transferred)
     */
    @Transactional
    public WarehouseInventoryDto reduceStock(Long warehouseId, Long medicineId, Integer qty) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        MedicineLookup medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        WarehouseInventory inventory = inventoryRepository
                .findByWarehouseAndMedicine(warehouse, medicine)
                .orElseThrow(() -> new RuntimeException("Inventory not found for this medicine"));

        if (inventory.getTotalQty() < qty) {
            throw new RuntimeException("Insufficient stock. Available: " + inventory.getTotalQty());
        }

        inventory.setTotalQty(inventory.getTotalQty() - qty);
        inventoryRepository.save(inventory);

        return mapToDto(inventory);
    }

    /**
     * Update inventory quantity directly (for adjustments)
     */
    @Transactional
    public WarehouseInventoryDto updateInventory(Long inventoryId, Integer newQty, Integer minimumQty) {
        WarehouseInventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (newQty != null) {
            inventory.setTotalQty(newQty);
        }
        if (minimumQty != null) {
            inventory.setMinimumQty(minimumQty);
        }

        inventoryRepository.save(inventory);
        return mapToDto(inventory);
    }

    /**
     * Get all inventory for a warehouse
     */
    public List<WarehouseInventoryDto> getInventoryByWarehouse(Long warehouseId) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return inventoryRepository.findByWarehouse(warehouse).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get inventory for a specific medicine in a warehouse
     */
    public WarehouseInventoryDto getInventoryByMedicine(Long warehouseId, Long medicineId) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        MedicineLookup medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        WarehouseInventory inventory = inventoryRepository
                .findByWarehouseAndMedicine(warehouse, medicine)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        return mapToDto(inventory);
    }

    /**
     * Get low stock items (qty <= minimumQty or qty = 0)
     */
    public List<WarehouseInventoryDto> getLowStockItems(Long warehouseId) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return inventoryRepository.findByWarehouse(warehouse).stream()
                .filter(inv -> inv.getTotalQty() <= inv.getMinimumQty())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get available stock items (qty > 0)
     */
    public List<WarehouseInventoryDto> getAvailableStock(Long warehouseId) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return inventoryRepository.findByWarehouseAndTotalQtyGreaterThan(warehouse, 0).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private WarehouseInventoryDto mapToDto(WarehouseInventory inventory) {
        return WarehouseInventoryDto.builder()
                .id(inventory.getId())
                .warehouseId(inventory.getWarehouse().getId())
                .warehouseName(inventory.getWarehouse().getName())
                .medicineId(inventory.getMedicine().getMedicineId())
                .medicineName(inventory.getMedicine().getName())
                .medicineType(inventory.getMedicine().getType())
                .totalQty(inventory.getTotalQty())
                .minimumQty(inventory.getMinimumQty())
                .updatedAt(inventory.getUpdatedAt() != null ? inventory.getUpdatedAt().format(DATE_FORMATTER) : null)
                .build();
    }
}
