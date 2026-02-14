package health.camp.service;

import health.camp.dto.medicine.MedicineResponse;
import health.camp.model.Medicine;
import health.camp.repository.MedicineRepository;
import health.camp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Page<MedicineResponse> list(String search, String category, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return medicineRepository.findBySearch(search, pageable).map(this::toResponse);
        }
        if (category != null && !category.isBlank()) {
            return medicineRepository.findByCategory(category, pageable).map(this::toResponse);
        }
        return medicineRepository.findAll(pageable).map(this::toResponse);
    }

    public MedicineResponse getById(String id) {
        Medicine m = medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicine", id));
        return toResponse(m);
    }

    private MedicineResponse toResponse(Medicine m) {
        MedicineResponse dto = new MedicineResponse();
        dto.setId(m.getId());
        dto.setName(m.getName());
        dto.setCode(m.getCode());
        dto.setCategory(m.getCategory());
        dto.setUnitPrice(m.getUnitPrice());
        return dto;
    }
}
