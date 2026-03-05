package health.camp.controller;

import health.camp.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")  // Allow CORS for all origins (adjust as needed)
public class DashboardController {
    private final PatientService patientService;

    @GetMapping
    public DashboardResponse getDashboardCounts() {
        long totalRegistrations = patientService.countAllPatients();
        int patientsAtDoctor = 5; // hardcoded
        int patientsAtPharmacy = 6; // hardcoded
        int patientsAtCashier = 2; // hardcoded
        int patientsAtInsulin = 12; // hardcoded
        int completed = 56; // hardcoded
        return new DashboardResponse(totalRegistrations, patientsAtDoctor, patientsAtPharmacy, patientsAtCashier, patientsAtInsulin, completed);
    }

    @Data
    @AllArgsConstructor
    public static class DashboardResponse {
        private long totalRegistrations;
        private int patientsAtDoctor;
        private int patientsAtPharmacy;
        private int patientsAtCashier;
        private int patientsAtInsulin;
        private int completed;
    }
}
