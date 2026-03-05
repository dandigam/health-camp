package health.camp.controller;
import health.camp.entity.LkpCondition;
import health.camp.entity.LkpLifestyle;
import health.camp.entity.LkpSymptom;
import health.camp.service.LookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lookup")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LookupController {

    private final LookupService lookupService;

    // GET /api/lookup/symptoms
    @GetMapping("/symptoms")
    public List<LkpSymptom> getAllSymptoms() {
        return lookupService.getAllSymptoms();
    }

    // GET /api/lookup/conditions
    @GetMapping("/conditions")
    public List<LkpCondition> getAllConditions() {
        return lookupService.getAllConditions();
    }

    // GET /api/lookup/lifestyles
    @GetMapping("/lifestyles")
    public List<LkpLifestyle> getAllLifestyles() {
        return lookupService.getAllLifestyles();
    }

}
