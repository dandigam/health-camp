package health.camp.service;

import health.camp.dto.stock.InvoiceDto;
import health.camp.dto.stock.InvoiceStockDto;
import health.camp.entity.*;
import health.camp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceStockRepository invoiceStockRepository;
    private final SupplierRepository supplierRepository;
    private final WareHouseRepository wareHouseRepository;
    private final MedicineRepository medicineRepository;
    private final WarehouseInventoryRepository warehouseInventoryRepository;

    @Transactional
    public InvoiceDto createInvoice(InvoiceDto dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + dto.getSupplierId()));

        WareHouse warehouse = wareHouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + dto.getWarehouseId()));

        Invoice invoice = Invoice.builder()
                .supplier(supplier)
                .paymentMode(dto.getPaymentMode())
                .invoiceNumber(dto.getInvoiceNumber())
                .invoiceAmount(dto.getInvoiceAmount())
                .invoiceDate(dto.getInvoiceDate())
                .warehouse(warehouse)
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Add stock items and update warehouse inventory
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (InvoiceStockDto itemDto : dto.getItems()) {
                MedicineLookup medicine;

                // Check if medicine already exists or needs to be created
                if (Boolean.FALSE.equals(itemDto.getIsAlreadyExist())) {
                    // Medicine doesn't exist - create new medicine in lookup table
                    if (itemDto.getMedicineName() == null || itemDto.getMedicineName().trim().isEmpty()) {
                        throw new RuntimeException("Medicine name is required when isAlreadyExist is false");
                    }

                    // Check if medicine with same name already exists
                    Optional<MedicineLookup> existingMedicine = medicineRepository.findByName(itemDto.getMedicineName().trim());
                    if (existingMedicine.isPresent()) {
                        medicine = existingMedicine.get();
                    } else {
                        // Create new medicine
                        MedicineLookup newMedicine = MedicineLookup.builder()
                                .name(itemDto.getMedicineName().trim())
                                .type(itemDto.getMedicineType())
                                .build();
                        medicine = medicineRepository.save(newMedicine);
                    }
                } else {
                    // Medicine exists - fetch by ID
                    if (itemDto.getMedicineId() == null) {
                        throw new RuntimeException("Medicine ID is required when isAlreadyExist is true");
                    }
                    medicine = medicineRepository.findById(itemDto.getMedicineId())
                            .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + itemDto.getMedicineId()));
                }

                // Save invoice stock item
                InvoiceStock stock = InvoiceStock.builder()
                        .invoice(savedInvoice)
                        .medicine(medicine)
                        .hsnNo(itemDto.getHsnNo())
                        .batchNo(itemDto.getBatchNo())
                        .expDate(itemDto.getExpDate())
                        .quantity(itemDto.getQuantity())
                        .warehouse(warehouse)
                        .build();

                invoiceStockRepository.save(stock);

                // Update warehouse inventory - add quantity to existing or create new
                addToWarehouseInventory(warehouse, medicine, itemDto.getQuantity());
            }
        }

        return mapToDto(savedInvoice);
    }

    /**
     * Add quantity to warehouse inventory. If entry exists, update quantity; otherwise create new.
     */
    private void addToWarehouseInventory(WareHouse warehouse, MedicineLookup medicine, int qty) {
        Optional<WarehouseInventory> existingInventory = warehouseInventoryRepository
                .findByWarehouseAndMedicine(warehouse, medicine);

        if (existingInventory.isPresent()) {
            // Update existing inventory - add the new quantity
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

    @Transactional
    public InvoiceDto updateInvoice(Long id, InvoiceDto dto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + dto.getSupplierId()));

        WareHouse warehouse = wareHouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + dto.getWarehouseId()));

        invoice.setSupplier(supplier);
        invoice.setPaymentMode(dto.getPaymentMode());
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setInvoiceAmount(dto.getInvoiceAmount());
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setWarehouse(warehouse);

        invoiceRepository.save(invoice);

        return mapToDto(invoice);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        
        // Delete all associated stock items first
        invoiceStockRepository.deleteAll(invoiceStockRepository.findByInvoiceId(id));
        
        invoiceRepository.delete(invoice);
    }

    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        return mapToDto(invoice);
    }

    public List<InvoiceDto> getInvoicesByWarehouse(Long warehouseId) {
        return invoiceRepository.findByWarehouseId(warehouseId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<InvoiceDto> getInvoicesBySupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + supplierId));
        return invoiceRepository.findBySupplier(supplier).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<InvoiceDto> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Invoice Stock operations

    @Transactional
    public InvoiceStockDto addStockItem(Long invoiceId, InvoiceStockDto dto) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + invoiceId));

        MedicineLookup medicine = medicineRepository.findById(dto.getMedicineId())
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + dto.getMedicineId()));

        WareHouse warehouse = wareHouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + dto.getWarehouseId()));

        InvoiceStock stock = InvoiceStock.builder()
                .invoice(invoice)
                .medicine(medicine)
                .hsnNo(dto.getHsnNo())
                .batchNo(dto.getBatchNo())
                .expDate(dto.getExpDate())
                .quantity(dto.getQuantity())
                .warehouse(warehouse)
                .build();

        InvoiceStock savedStock = invoiceStockRepository.save(stock);

        return mapStockToDto(savedStock);
    }

    @Transactional
    public InvoiceStockDto updateStockItem(Long stockId, InvoiceStockDto dto) {
        InvoiceStock stock = invoiceStockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock item not found with id: " + stockId));

        MedicineLookup medicine = medicineRepository.findById(dto.getMedicineId())
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + dto.getMedicineId()));

        stock.setMedicine(medicine);
        stock.setHsnNo(dto.getHsnNo());
        stock.setBatchNo(dto.getBatchNo());
        stock.setExpDate(dto.getExpDate());
        stock.setQuantity(dto.getQuantity());

        InvoiceStock savedStock = invoiceStockRepository.save(stock);

        return mapStockToDto(savedStock);
    }

    @Transactional
    public void deleteStockItem(Long stockId) {
        InvoiceStock stock = invoiceStockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock item not found with id: " + stockId));
        invoiceStockRepository.delete(stock);
    }

    public List<InvoiceStockDto> getStockItemsByInvoice(Long invoiceId) {
        return invoiceStockRepository.findByInvoiceId(invoiceId).stream()
                .map(this::mapStockToDto)
                .collect(Collectors.toList());
    }

    public List<InvoiceStockDto> getStockItemsByWarehouse(Long warehouseId) {
        return invoiceStockRepository.findByWarehouseId(warehouseId).stream()
                .map(this::mapStockToDto)
                .collect(Collectors.toList());
    }

    public List<InvoiceStockDto> getExpiredStock(Long warehouseId) {
        return invoiceStockRepository.findExpiredStockByWarehouseId(warehouseId, LocalDate.now()).stream()
                .map(this::mapStockToDto)
                .collect(Collectors.toList());
    }

    public List<InvoiceStockDto> getExpiringStock(Long warehouseId, int daysAhead) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(daysAhead);
        return invoiceStockRepository.findExpiringStockByWarehouseId(warehouseId, startDate, endDate).stream()
                .map(this::mapStockToDto)
                .collect(Collectors.toList());
    }

    // Mapper methods

    private InvoiceDto mapToDto(Invoice invoice) {
        List<InvoiceStockDto> items = invoiceStockRepository.findByInvoiceId(invoice.getId()).stream()
                .map(this::mapStockToDto)
                .collect(Collectors.toList());

        return InvoiceDto.builder()
                .id(invoice.getId())
                .supplierId(invoice.getSupplier().getSupplierId())
                .supplierName(invoice.getSupplier().getName())
                .paymentMode(invoice.getPaymentMode())
                .invoiceNumber(invoice.getInvoiceNumber())
                .invoiceAmount(invoice.getInvoiceAmount())
                .invoiceDate(invoice.getInvoiceDate())
                .warehouseId(invoice.getWarehouse().getId())
                .warehouseName(invoice.getWarehouse().getName())
                .items(items)
                .createdBy(invoice.getCreatedBy())
                .createdAt(invoice.getCreatedAt() != null ? 
                        invoice.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null)
                .build();
    }

    private InvoiceStockDto mapStockToDto(InvoiceStock stock) {
        return InvoiceStockDto.builder()
                .id(stock.getId())
                .invoiceId(stock.getInvoice().getId())
                .medicineId(stock.getMedicine().getMedicineId())
                .medicineName(stock.getMedicine().getName())
                .hsnNo(stock.getHsnNo())
                .batchNo(stock.getBatchNo())
                .expDate(stock.getExpDate())
                .quantity(stock.getQuantity())
                .warehouseId(stock.getWarehouse().getId())
                .warehouseName(stock.getWarehouse().getName())
                .build();
    }
}
