package health.camp.service;

import health.camp.entity.MedicalConditionLookup;
import health.camp.repository.MedicalConditionLookupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalConditionLookupService {
    private final MedicalConditionLookupRepository repository;

    public List<MedicalConditionLookup> getAll() {
        return repository.findAll();
    }
}
