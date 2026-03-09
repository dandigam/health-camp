package health.camp.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierDto {

    private Long id;
    private String name;
    private String contact;
    private Long stateId;
    private Long districtId;
    private Long mandalId;
    private String state;
    private String district;
    private String mandal;
    private String address;
    private String pinCode;
    private String status;
    private String email;

    // NEW: list of medicines to add to lookup and link
    private List<MedicineDto> medicines;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MedicineDto {
        private Long id;
        private String name;
        private String type; // tablet, syrup, injection, etc.
        private String strength; // e.g., 50mg, 100mg
        private String unit; // e.g., mg, g, ml
        private String manufacturer;
        private Long medicineId;
        private Integer currentQty; // Current inventory quantity in warehouse
    }
}
