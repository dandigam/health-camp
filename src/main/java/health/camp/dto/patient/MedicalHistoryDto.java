package health.camp.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryDto {
    private List<String> conditions; // List of condition IDs as strings
    private String previousHospital;
    private String currentMedications;
    private String pastSurgery;
    private String otherDetails; // Only used for 'Other' condition
}
