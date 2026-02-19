package health.camp.service;

import health.camp.dto.SupplierDto;
import health.camp.entity.MedicineLookup;
import health.camp.entity.Supplier;
import health.camp.entity.WareHouse;
import health.camp.entity.WarehouseSupplierMedicine;
import health.camp.model.enums.Status;
import health.camp.repository.DistrictRepository;
import health.camp.repository.MandalRepository;
import health.camp.repository.MedicineRepository;
import health.camp.repository.StateRepository;
import health.camp.repository.SupplierRepository;
import health.camp.repository.WareHouseRepository;
import health.camp.repository.WarehouseSupplierMedicineRepository;
import health.camp.repository.WarehouseInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final WareHouseRepository wareHouseRepository;
    private final MedicineRepository medicineRepository;
    private final WarehouseSupplierMedicineRepository warehouseSupplierMedicineRepository;
    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final MandalRepository mandalRepository;

    @Transactional
    public SupplierDto addSupplierWithMedicines(Long warehouseId, SupplierDto dto) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        // Add Supplier
        Supplier supplier = Supplier.builder()
                .warehouse(warehouse)
                .name(dto.getName())
                .contactNumber(dto.getContact())
                .stateId(dto.getStateId())
                .districtId(dto.getDistrictId())
                .mandalId(dto.getMandalId())
                .city(dto.getAddress())
                .pinCode(dto.getPinCode())
                .email(dto.getEmail())
                .status(Status.valueOf(dto.getStatus()))
                .build();

        Supplier savedSupplier = supplierRepository.save(supplier);

        // Add Medicines to Lookup and link in WarehouseSupplierMedicine
        if (dto.getMedicines() != null) {
            for (SupplierDto.MedicineDto mDto : dto.getMedicines()) {
                // check if medicine exists
                MedicineLookup medicine = medicineRepository.findByName(mDto.getName())
                        .orElseGet(() -> {
                            // create new medicine
                            MedicineLookup newMed = MedicineLookup.builder()
                                    .name(mDto.getName())
                                    .type(mDto.getType())
                                    .build();
                            return medicineRepository.save(newMed);
                        });

                // Create warehouse-supplier-medicine relation
                WarehouseSupplierMedicine relation = WarehouseSupplierMedicine.builder()
                        .warehouse(warehouse)
                        .supplier(savedSupplier)
                        .medicine(medicine)
                        .status(Status.ACTIVE)
                        .build();

                warehouseSupplierMedicineRepository.save(relation);
            }
        }

        dto.setId(savedSupplier.getSupplierId());
        return dto;
    }


    @Transactional
    public SupplierDto updateSupplier(Long warehouseId, Long id, SupplierDto dto) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setName(dto.getName());
        supplier.setContactNumber(dto.getContact());
        supplier.setStateId(dto.getStateId());
        supplier.setDistrictId(dto.getDistrictId());
        supplier.setMandalId(dto.getMandalId());
        supplier.setCity(dto.getAddress());
        supplier.setPinCode(dto.getPinCode());
        supplier.setEmail(dto.getEmail());
        supplier.setStatus(Status.valueOf(dto.getStatus()));

        supplierRepository.save(supplier);

        // Handle medicines - update existing or add new
        if (dto.getMedicines() != null) {
            for (SupplierDto.MedicineDto mDto : dto.getMedicines()) {
                if (mDto.getId() != null) {
                    // Update existing medicine name/type in lookup
                    MedicineLookup medicine = medicineRepository.findById(mDto.getId())
                            .orElseThrow(() -> new RuntimeException("Medicine not found: " + mDto.getId()));
                    medicine.setName(mDto.getName());
                    medicine.setType(mDto.getType());
                    medicineRepository.save(medicine);
                } else {
                    // Add new medicine
                    MedicineLookup medicine = medicineRepository.findByName(mDto.getName())
                            .orElseGet(() -> {
                                MedicineLookup newMed = MedicineLookup.builder()
                                        .name(mDto.getName())
                                        .type(mDto.getType())
                                        .build();
                                return medicineRepository.save(newMed);
                            });

                    // Check if relation already exists
                    boolean relationExists = warehouseSupplierMedicineRepository
                            .findByWarehouseAndSupplierAndMedicine(warehouse, supplier, medicine)
                            .isPresent();

                    if (!relationExists) {
                        WarehouseSupplierMedicine relation = WarehouseSupplierMedicine.builder()
                                .warehouse(warehouse)
                                .supplier(supplier)
                                .medicine(medicine)
                                .status(Status.ACTIVE)
                                .build();
                        warehouseSupplierMedicineRepository.save(relation);
                    }
                }
            }
        }

        dto.setId(supplier.getSupplierId());
        return dto;
    }

    @Transactional
    public void deleteSupplier(Long warehouseId, Long id) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        
        // Delete all medicine relations for this supplier in this warehouse
        List<WarehouseSupplierMedicine> relations = warehouseSupplierMedicineRepository
                .findByWarehouseAndSupplier(warehouse, supplier);
        warehouseSupplierMedicineRepository.deleteAll(relations);
        
        // Delete the supplier
        supplierRepository.delete(supplier);
    }

    public SupplierDto getSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        // Fetch names from IDs
        String stateName = supplier.getStateId() != null ?
                stateRepository.findById(supplier.getStateId()).map(s -> s.getName()).orElse(null) : null;
        String districtName = supplier.getDistrictId() != null ?
                districtRepository.findById(supplier.getDistrictId()).map(d -> d.getName()).orElse(null) : null;
        String mandalName = supplier.getMandalId() != null ?
                mandalRepository.findById(supplier.getMandalId()).map(m -> m.getName()).orElse(null) : null;

        return SupplierDto.builder()
                .id(supplier.getSupplierId())
                .name(supplier.getName())
                .contact(supplier.getContactNumber())
                .stateId(supplier.getStateId())
                .districtId(supplier.getDistrictId())
                .mandalId(supplier.getMandalId())
                .state(stateName)
                .district(districtName)
                .mandal(mandalName)
                .address(supplier.getCity())
                .pinCode(supplier.getPinCode())
                .email(supplier.getEmail())
                .status(supplier.getStatus().name())
                .build();
    }

    public List<SupplierDto> getSuppliersByWarehouse(Long warehouseId) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return supplierRepository.findByWarehouse(warehouse).stream()
                .map(supplier -> {
                    // Fetch medicines for this supplier with current inventory qty
                    List<SupplierDto.MedicineDto> medicines = warehouseSupplierMedicineRepository
                            .findBySupplier(supplier).stream()
                            .map(wsm -> {
                                // Get current inventory quantity for this medicine in this warehouse
                                Integer currentQty = warehouseInventoryRepository
                                        .findByWarehouseAndMedicine(warehouse, wsm.getMedicine())
                                        .map(inv -> inv.getTotalQty())
                                        .orElse(0);
                                
                                return SupplierDto.MedicineDto.builder()
                                        .id(wsm.getMedicine().getMedicineId())
                                        .name(wsm.getMedicine().getName())
                                        .type(wsm.getMedicine().getType())
                                        .currentQty(currentQty)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    // Fetch names from IDs
                    String stateName = supplier.getStateId() != null ?
                            stateRepository.findById(supplier.getStateId()).map(s -> s.getName()).orElse(null) : null;
                    String districtName = supplier.getDistrictId() != null ?
                            districtRepository.findById(supplier.getDistrictId()).map(d -> d.getName()).orElse(null) : null;
                    String mandalName = supplier.getMandalId() != null ?
                            mandalRepository.findById(supplier.getMandalId()).map(m -> m.getName()).orElse(null) : null;

                    return SupplierDto.builder()
                            .id(supplier.getSupplierId())
                            .name(supplier.getName())
                            .contact(supplier.getContactNumber())
                            .stateId(supplier.getStateId())
                            .districtId(supplier.getDistrictId())
                            .mandalId(supplier.getMandalId())
                            .state(stateName)
                            .district(districtName)
                            .mandal(mandalName)
                            .address(supplier.getCity())
                            .pinCode(supplier.getPinCode())
                            .email(supplier.getEmail())
                            .status(supplier.getStatus().name())
                            .medicines(medicines)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeMedicineFromSupplier(Long warehouseId, Long supplierId, Long medicineId) {
        WareHouse warehouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        MedicineLookup medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        WarehouseSupplierMedicine relation = warehouseSupplierMedicineRepository
                .findByWarehouseAndSupplierAndMedicine(warehouse, supplier, medicine)
                .orElseThrow(() -> new RuntimeException("Medicine not linked to this supplier/warehouse"));

        warehouseSupplierMedicineRepository.delete(relation);
    }
}
