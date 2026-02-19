package health.camp.controller;

import health.camp.dto.StateHierarchyDTO;

import health.camp.service.StateService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StateController {

    private final StateService stateService;

    @GetMapping("/hierarchy")
    public List<StateHierarchyDTO> getHierarchy() {
        return stateService.getFullHierarchy();
    }
}

