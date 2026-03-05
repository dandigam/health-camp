package health.camp.service;

import health.camp.dto.EncounterSubjectDto;
import health.camp.entity.*;
import health.camp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EncounterSubjectService {

    private final EncounterSubjectRepository repository;
    private final EncounterRepository encounterRepository;
    private final LkpSymptomRepository symptomRepository;
    private final LkpConditionRepository conditionRepository;
    private final LkpLifestyleRepository lifestyleRepository;

    /*
     * GET BY ENCOUNTER
     */
    public Optional<EncounterSubjectDto> getByEncounterId(Long encounterId) {
        return repository.findByEncounterId(encounterId)
                .map(this::toDto);
    }

    /*
     * SAVE OR UPDATE
     */
    @Transactional
    public EncounterSubjectDto saveOrUpdate(EncounterSubjectDto dto) {

        EncounterSubject entity;

        if (dto.getId() != null) {

            entity = repository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Subject Assessment not found"));

        } else {

            entity = new EncounterSubject();

            Encounter encounter = encounterRepository.findById(dto.getEncounterId())
                    .orElseThrow(() -> new RuntimeException("Encounter not found"));

            entity.setEncounter(encounter);
        }

        entity.setChiefComplaintText(dto.getChiefComplaintText());
        entity.setAdditionalNotes(dto.getAdditionalNotes());

        repository.save(entity);

        updateSymptoms(entity, dto.getSymptomIds());
        updateConditions(entity, dto.getConditionIds());
        updateLifestyles(entity, dto.getLifestyleIds());

        return toDto(entity);
    }

    /*
     * UPDATE SYMPTOMS
     */
    private void updateSymptoms(EncounterSubject subject, Set<Long> ids) {

        if (ids == null) return;

        Set<EncounterSymptom> list = new HashSet<>();

        for (Long id : ids) {

            LkpSymptom symptom = symptomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Symptom not found"));

            EncounterSymptom es = new EncounterSymptom();
            es.setEncounterSubject(subject);
            es.setSymptom(symptom);

            list.add(es);
        }

        subject.setSymptoms(list);
    }

    /*
     * UPDATE CONDITIONS
     */
    private void updateConditions(EncounterSubject subject, Set<Long> ids) {

        if (ids == null) return;

        Set<EncounterCondition> list = new HashSet<>();

        for (Long id : ids) {

            LkpCondition condition = conditionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Condition not found"));

            EncounterCondition ec = new EncounterCondition();
            ec.setEncounterSubject(subject);
            ec.setCondition(condition);

            list.add(ec);
        }

        subject.setConditions(list);
    }

    /*
     * UPDATE LIFESTYLE
     */
    private void updateLifestyles(EncounterSubject subject, Set<Long> ids) {

        if (ids == null) return;

        Set<EncounterLifestyle> list = new HashSet<>();

        for (Long id : ids) {

            LkpLifestyle lifestyle = lifestyleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Lifestyle not found"));

            EncounterLifestyle el = new EncounterLifestyle();
            el.setEncounterSubject(subject);
            el.setLifestyle(lifestyle);

            list.add(el);
        }

        subject.setLifestyles(list);
    }

    /*
     * ENTITY → DTO
     */
    private EncounterSubjectDto toDto(EncounterSubject entity) {

        EncounterSubjectDto dto = new EncounterSubjectDto();

        dto.setId(entity.getId());
        dto.setEncounterId(entity.getEncounter().getId());
        dto.setChiefComplaintText(entity.getChiefComplaintText());
        dto.setAdditionalNotes(entity.getAdditionalNotes());
        dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getSymptoms() != null) {

            dto.setSymptoms(entity.getSymptoms()
                    .stream()
                    .map(s -> s.getSymptom().getName())
                    .collect(Collectors.toSet()));

            dto.setSymptomIds(entity.getSymptoms()
                    .stream()
                    .map(s -> s.getSymptom().getId())
                    .collect(Collectors.toSet()));
        }

        if (entity.getConditions() != null) {

            dto.setConditions(entity.getConditions()
                    .stream()
                    .map(c -> c.getCondition().getName())
                    .collect(Collectors.toSet()));

            dto.setConditionIds(entity.getConditions()
                    .stream()
                    .map(c -> c.getCondition().getId())
                    .collect(Collectors.toSet()));
        }

        if (entity.getLifestyles() != null) {

            dto.setLifestyles(entity.getLifestyles()
                    .stream()
                    .map(l -> l.getLifestyle().getName())
                    .collect(Collectors.toSet()));

            dto.setLifestyleIds(entity.getLifestyles()
                    .stream()
                    .map(l -> l.getLifestyle().getId())
                    .collect(Collectors.toSet()));
        }

        return dto;
    }
}