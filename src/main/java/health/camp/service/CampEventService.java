package health.camp.service;

import health.camp.entity.CampEvent;
import health.camp.repository.CampEventRepository;
import health.camp.repository.DoctorCampRepository;
import health.camp.repository.VolunteerCampRepository;
import health.camp.repository.DoctorRepository;
import health.camp.repository.VolunteerRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampEventService {

    /**
     * Get all camp events
     */
    public List<CampEvent> getAllCampEvents() {
        return campEventRepository.findAll();
    }

    /**
     * Update the status of a camp event by ID
     */
    public CampEvent updateEventStatus(Long eventId, String status) {
        return campEventRepository.findById(eventId).map(event -> {
            event.setStatus(status);
            return campEventRepository.save(event);
        }).orElse(null);
    }

    public CampEvent getCampEventById(Long eventId) {
        return campEventRepository.findById(eventId).orElse(null);
    }

    private final CampEventRepository campEventRepository;
    private final DoctorCampRepository doctorCampRepository;
    private final VolunteerCampRepository volunteerCampRepository;
    private final DoctorRepository doctorRepository;
    private final VolunteerRepository volunteerRepository;

    public CampEvent saveCampEvent(CampEvent campEvent) {
        return campEventRepository.save(campEvent);
    }

    /**
     * Assigns doctors to a camp by updating the DOCTOR_CAMP table.
     * Removes previous assignments and adds new ones for the given camp.
     */
    public void assignDoctorsToCamp(health.camp.entity.Camp camp, java.util.List<Long> doctorIds) {
        if (camp == null || camp.getId() == null || doctorIds == null) return;

        // Remove existing doctor assignments for this camp
        java.util.List<health.camp.entity.DoctorCamp> existing = doctorCampRepository.findAll();
        existing.stream()
            .filter(dc -> dc.getCamp() != null && camp.getId().equals(dc.getCamp().getId()))
            .forEach(dc -> doctorCampRepository.delete(dc));

        // Add new doctor assignments
        for (Long doctorId : doctorIds) {
            health.camp.entity.Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
            if (doctor != null) {
                health.camp.entity.DoctorCamp doctorCamp = health.camp.entity.DoctorCamp.builder()
                        .doctor(doctor)
                        .camp(camp)
                        .active(true)
                        .isDefault(false)
                        .build();
                doctorCampRepository.save(doctorCamp);
            }
        }
    }

           /**
         * Assigns staff (volunteers) to a camp by updating the VOLUNTEER_CAMP table.
         * Removes previous assignments and adds new ones for the given camp.
         */
        public void assignStaffToCamp(health.camp.entity.Camp camp, java.util.List<Long> staffIds) {
            if (camp == null || camp.getId() == null || staffIds == null) return;

            // Remove existing staff assignments for this camp
            java.util.List<health.camp.entity.VolunteerCamp> existing = volunteerCampRepository.findAll();
            existing.stream()
                .filter(vc -> vc.getCamp() != null && camp.getId().equals(vc.getCamp().getId()))
                .forEach(vc -> volunteerCampRepository.delete(vc));

            // Add new staff assignments
            for (Long staffId : staffIds) {
                health.camp.entity.Volunteer volunteer = volunteerRepository.findById(staffId).orElse(null);
                if (volunteer != null) {
                    health.camp.entity.VolunteerCamp volunteerCamp = health.camp.entity.VolunteerCamp.builder()
                            .volunteer(volunteer)
                            .camp(camp)
                            .build();
                    volunteerCampRepository.save(volunteerCamp);
                }
            }
        }


}