package health.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StateHierarchyDTO {
    private Long id;
    private String name;
    private List<DistrictDTO> districts;
}
