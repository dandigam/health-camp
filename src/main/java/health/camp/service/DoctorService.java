package health.camp.service;

import health.camp.entity.Doctor;
import health.camp.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor) {
        // If doctor has associated camps, handle them
        if (doctor.getDoctorCamps() != null) {
            doctor.getDoctorCamps().forEach(dc -> dc.setDoctor(doctor));
        }
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
