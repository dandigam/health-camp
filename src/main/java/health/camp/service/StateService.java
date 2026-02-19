package health.camp.service;
import health.camp.dto.StateHierarchyDTO;
import health.camp.dto.DistrictDTO;
import health.camp.dto.MandalDTO;
import health.camp.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    public List<StateHierarchyDTO> getFullHierarchy() {

        return stateRepository.findAll().stream().map(state -> {

            List<DistrictDTO> districtDTOs = state.getDistricts().stream().map(district -> {

                List<MandalDTO> mandalDTOs = district.getMandals().stream()
                        .map(mandal -> new MandalDTO(
                                mandal.getId(),
                                mandal.getName()
                        )).toList();

                return new DistrictDTO(
                        district.getId(),
                        district.getName(),
                        mandalDTOs
                );

            }).toList();

            return new StateHierarchyDTO(
                    state.getId(),
                    state.getName(),
                    districtDTOs
            );

        }).toList();
    }
}
