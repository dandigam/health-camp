package health.camp.service;

import health.camp.entity.LkpCondition;
import health.camp.entity.LkpLifestyle;
import health.camp.entity.LkpSymptom;
import health.camp.repository.LkpConditionRepository;
import health.camp.repository.LkpLifestyleRepository;
import health.camp.repository.LkpSymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupService {

    private final LkpSymptomRepository symptomRepository;
    private final LkpConditionRepository conditionRepository;
    private final LkpLifestyleRepository lifestyleRepository;

    public List<LkpSymptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }

    public List<LkpCondition> getAllConditions() {
        return conditionRepository.findAll();
    }


    public List<LkpLifestyle> getAllLifestyles() {
        return lifestyleRepository.findAll();
    }
}
