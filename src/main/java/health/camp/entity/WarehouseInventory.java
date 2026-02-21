package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "warehouse_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WarehouseInventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WareHouse warehouse;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private MedicineLookup medicine;

    @Column(name = "total_qty", nullable = false)
    private Integer totalQty;

    @Column(name = "minimum_qty")
    private Integer minimumQty;

    @PrePersist
    protected void onCreate() {
        if (totalQty == null) {
            totalQty = 0;
        }
    }
}
