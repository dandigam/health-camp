package health.camp.service;

import health.camp.dto.reports.ReportsOverviewResponse;
import health.camp.model.enums.CampStatus;
import health.camp.repository.CampRepository;
import health.camp.repository.ConsultationRepository;
import health.camp.repository.DiscountRepository;
import health.camp.repository.DoctorRepository;
import health.camp.repository.PatientRepository;
import health.camp.repository.PaymentRepository;
import health.camp.repository.PrescriptionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReportsService {

    private final CampRepository campRepository;
    private final PatientRepository patientRepository;
    private final ConsultationRepository consultationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PaymentRepository paymentRepository;
    private final DiscountRepository discountRepository;
    private final DoctorRepository doctorRepository;

    public ReportsService(CampRepository campRepository, PatientRepository patientRepository,
                          ConsultationRepository consultationRepository, PrescriptionRepository prescriptionRepository,
                          PaymentRepository paymentRepository, DiscountRepository discountRepository,
                          DoctorRepository doctorRepository) {
        this.campRepository = campRepository;
        this.patientRepository = patientRepository;
        this.consultationRepository = consultationRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.paymentRepository = paymentRepository;
        this.discountRepository = discountRepository;
        this.doctorRepository = doctorRepository;
    }

    public ReportsOverviewResponse getOverview(String campId) {
        ReportsOverviewResponse r = new ReportsOverviewResponse();
        long totalCamps = campRepository.count();
        r.setTotalCamps(totalCamps);
        //r.setActiveCamps(campRepository.findByStatus(CampStatus.active, Pageable.unpaged()).getTotalElements());
        r.setTotalPatients(patientRepository.count());
        r.setTotalConsultations(consultationRepository.count());
        r.setTotalPrescriptions(prescriptionRepository.count());
        BigDecimal totalCollection = paymentRepository.findAll(Pageable.unpaged()).getContent().stream()
                .map(p -> p.getPaidAmount() != null ? p.getPaidAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        r.setTotalCollection(totalCollection);
        BigDecimal pending = paymentRepository.findAll(Pageable.unpaged()).getContent().stream()
                .map(p -> p.getPendingAmount() != null ? p.getPendingAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        r.setPendingPayments(pending);
        r.setTotalDiscounts(discountRepository.count());
        r.setDoctorsCount(doctorRepository.count());
        return r;
    }
}
