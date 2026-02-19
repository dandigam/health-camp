package health.camp.dto.supplier;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyOrderTrackingDto {

    private String month; // e.g., "February 2026"
    private int year;
    private int monthNumber;
    private int totalOrders;
    private List<SupplierOrderRequestDto> orders;
}
