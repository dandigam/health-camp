package health.camp.service;

import health.camp.dto.stock.StockItemRequest;
import health.camp.dto.stock.StockItemResponse;
import health.camp.model.Medicine;
import health.camp.model.StockItem;
import health.camp.model.Supplier;
import health.camp.repository.MedicineRepository;
import health.camp.repository.StockItemRepository;
import health.camp.repository.SupplierRepository;
import health.camp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StockService {

    private final StockItemRepository stockItemRepository;
    private final MedicineRepository medicineRepository;
    private final SupplierRepository supplierRepository;

    public StockService(StockItemRepository stockItemRepository, MedicineRepository medicineRepository,
                        SupplierRepository supplierRepository) {
        this.stockItemRepository = stockItemRepository;
        this.medicineRepository = medicineRepository;
        this.supplierRepository = supplierRepository;
    }

    public Page<StockItemResponse> list(String campId, String medicineId, Boolean lowStockOnly, Pageable pageable) {
        Page<StockItem> page;
        if (campId != null && !campId.isBlank()) {
            if (Boolean.TRUE.equals(lowStockOnly)) {
                page = stockItemRepository.findByCampIdAndQuantityLessThan(campId, 50, pageable);
            } else if (medicineId != null && !medicineId.isBlank()) {
                page = stockItemRepository.findByCampIdAndMedicineId(campId, medicineId, pageable);
            } else {
                page = stockItemRepository.findByCampId(campId, pageable);
            }
        } else if (medicineId != null && !medicineId.isBlank()) {
            page = stockItemRepository.findByMedicineId(medicineId, pageable);
        } else {
            page = stockItemRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    public StockItemResponse create(StockItemRequest request) {
        medicineRepository.findById(request.getMedicineId()).orElseThrow(() -> new ResourceNotFoundException("Medicine", request.getMedicineId()));
        supplierRepository.findById(request.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier", request.getSupplierId()));
        StockItem item = new StockItem();
        item.setId(UUID.randomUUID().toString());
        item.setMedicineId(request.getMedicineId());
        item.setCampId(request.getCampId());
        item.setQuantity(request.getQuantity());
        item.setBatchNumber(request.getBatchNumber());
        item.setExpiryDate(request.getExpiryDate());
        item.setPurchaseDate(request.getPurchaseDate());
        item.setSupplierId(request.getSupplierId());
        item = stockItemRepository.save(item);
        return toResponse(item);
    }

    private StockItemResponse toResponse(StockItem item) {
        StockItemResponse dto = new StockItemResponse();
        dto.setId(item.getId());
        dto.setMedicineId(item.getMedicineId());
        dto.setCampId(item.getCampId());
        dto.setQuantity(item.getQuantity());
        dto.setBatchNumber(item.getBatchNumber());
        dto.setExpiryDate(item.getExpiryDate());
        dto.setPurchaseDate(item.getPurchaseDate());
        dto.setSupplierId(item.getSupplierId());
        medicineRepository.findById(item.getMedicineId()).ifPresent(m -> {
            dto.setMedicineName(m.getName());
            dto.setMedicineCode(m.getCode());
        });
        supplierRepository.findById(item.getSupplierId()).ifPresent(s -> dto.setSupplierName(s.getName()));
        return dto;
    }
}
