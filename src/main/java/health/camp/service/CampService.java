package health.camp.service;

import health.camp.dto.CampDto;
import health.camp.dto.DoctorCampDto;
import health.camp.dto.VolunteerCampDto;
import health.camp.entity.Camp;
import health.camp.entity.Doctor;
import health.camp.entity.DoctorCamp;
import health.camp.entity.Volunteer;
import health.camp.entity.VolunteerCamp;
import health.camp.repository.CampRepository;
import health.camp.repository.DoctorRepository;
import health.camp.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampService {

        public Camp getCampById(Long id) {
            return campRepository.findById(id).orElse(null);
        }
    
    private final DoctorRepository doctorRepository;
    private final VolunteerRepository volunteerRepository;
    private final CampRepository campRepository;

    public Camp saveCampFromDto(CampDto dto) {
        
        Camp camp = (dto.getId() != null) ? campRepository.findById(dto.getId()).orElse(new Camp()) : new Camp();
        camp.setCampName(dto.getCampName());
        camp.setOrganizerName(dto.getOrganizerName());
        camp.setOrganizerPhone(dto.getOrganizerPhone());
        camp.setOrganizerEmail(dto.getOrganizerEmail());
        camp.setActive(dto.getActive());
        camp.setStateId(dto.getStateId());
        camp.setState(dto.getState());
        camp.setDistrictId(dto.getDistrictId());
        camp.setDistrict(dto.getDistrict());
        camp.setMandalId(dto.getMandalId());
        camp.setMandal(dto.getMandal());
        camp.setCity(dto.getCity());
        camp.setAddress(dto.getAddress());
        camp.setPinCode(dto.getPinCode());

        // Handle doctor associations
        if (dto.getDoctorList() != null) {
            if (camp.getDoctorCamps() == null) {
                camp.setDoctorCamps(new java.util.ArrayList<>());
            } else {
                camp.getDoctorCamps().clear();
            }
            for (Long doctorId : dto.getDoctorList()) {
                Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
                if (doctor != null) {
                    // Default: isDefault true if added during camp creation (template), active true if during event
                    DoctorCamp dc = DoctorCamp.builder()
                        .camp(camp)
                        .doctor(doctor)
                        .isDefault(true) // set true for template
                        .active(false)   // set true for event
                        .build();
                    camp.getDoctorCamps().add(dc);
                }
            }
        }

        // Handle volunteer associations
        if (dto.getStaffList() != null) {
            if (camp.getVolunteerCamps() == null) {
                camp.setVolunteerCamps(new java.util.ArrayList<>());
            } else {
                camp.getVolunteerCamps().clear();
            }
            for (Long volunteerId : dto.getStaffList()) {
                Volunteer volunteer = volunteerRepository.findById(volunteerId).orElse(null);
                if (volunteer != null) {
                    VolunteerCamp vc = VolunteerCamp.builder().camp(camp).volunteer(volunteer).build();
                    camp.getVolunteerCamps().add(vc);
                }
            }
        }

        return campRepository.save(camp);
    }


        public void addDoctorToCamp(DoctorCampDto dto) {
            Camp camp = campRepository.findById(dto.getCampId())
                .orElseThrow(() -> new RuntimeException("Camp not found"));
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
            DoctorCamp doctorCamp = DoctorCamp.builder()
                .camp(camp)
                .doctor(doctor)
                .build();
            camp.getDoctorCamps().add(doctorCamp);
            campRepository.save(camp);
        }

        public void addVolunteerToCamp(VolunteerCampDto dto) {
            Camp camp = campRepository.findById(dto.getCampId())
                .orElseThrow(() -> new RuntimeException("Camp not found"));
            Volunteer volunteer = volunteerRepository.findById(dto.getVolunteerId())
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));
            VolunteerCamp volunteerCamp = VolunteerCamp.builder()
                .camp(camp)
                .volunteer(volunteer)
                .build();
            camp.getVolunteerCamps().add(volunteerCamp);
            campRepository.save(camp);
        }

    public Camp saveCamp(Camp camp) {
        return campRepository.save(camp);
    }

    public List<Camp> getAllCamps() {
        return campRepository.findAll();
    }
}
