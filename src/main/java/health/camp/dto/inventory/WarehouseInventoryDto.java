package health.camp.dto.inventory;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseInventoryDto {

    private Long id;
    private Long warehouseId;
    private String warehouseName;
    private Long medicineId;
    private String medicineName;
    private String medicineType;
    private Integer totalQty;
    private Integer minimumQty;
    private String updatedAt;
    
    // For bulk add
    private List<InventoryItemDto> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InventoryItemDto {
        private Long medicineId;
        private Integer qty;
        private Integer minimumQty;
    }
}
