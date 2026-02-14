package health.camp.service;

import health.camp.dto.discount.DiscountResponse;
import health.camp.model.Discount;
import health.camp.repository.DiscountRepository;
import health.camp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public Page<DiscountResponse> list(String campId, String patientId, Pageable pageable) {
        Page<Discount> page;
        if (campId != null && !campId.isBlank()) {
            page = discountRepository.findByCampId(campId, pageable);
        } else if (patientId != null && !patientId.isBlank()) {
            page = discountRepository.findByPatientId(patientId, pageable);
        } else {
            page = discountRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    private DiscountResponse toResponse(Discount d) {
        DiscountResponse dto = new DiscountResponse();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setType(d.getType());
        dto.setValue(d.getValue());
        dto.setCampId(d.getCampId());
        dto.setPatientId(d.getPatientId());
        dto.setPrescriptionId(d.getPrescriptionId());
        dto.setAppliedBy(d.getAppliedBy());
        dto.setReason(d.getReason());
        dto.setCreatedAt(d.getCreatedAt());
        return dto;
    }
}
