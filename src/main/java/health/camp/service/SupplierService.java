package health.camp.service;

import health.camp.dto.supplier.SupplierRequest;
import health.camp.dto.supplier.SupplierResponse;
import health.camp.model.Supplier;
import health.camp.repository.SupplierRepository;
import health.camp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Page<SupplierResponse> list(Pageable pageable) {
        return supplierRepository.findAll(pageable).map(this::toResponse);
    }

    public SupplierResponse getById(String id) {
        Supplier s = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
        return toResponse(s);
    }

    public SupplierResponse create(SupplierRequest request) {
        Supplier s = new Supplier();
        s.setId(UUID.randomUUID().toString());
        s.setName(request.getName());
        s.setContact(request.getContact());
        s.setAddress(request.getAddress());
        s = supplierRepository.save(s);
        return toResponse(s);
    }

    private SupplierResponse toResponse(Supplier s) {
        SupplierResponse dto = new SupplierResponse();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setContact(s.getContact());
        dto.setAddress(s.getAddress());
        return dto;
    }
}
