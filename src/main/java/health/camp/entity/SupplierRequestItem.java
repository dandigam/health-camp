package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "supplier_request_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SupplierRequestItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_item_id")
    private Long requestItemId;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private SupplierRequest request;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private MedicineLookup medicine;

    @Column(name = "requested_quantity", nullable = false)
    private Integer requestedQuantity;

    @Column(name = "received_quantity")
    private Integer receivedQuantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private Status status; // PENDING, RECEIVED, PARTIAL

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = Status.PENDING;
        }
        if (receivedQuantity == null) {
            receivedQuantity = 0;
        }
    }

    public enum Status {
        PENDING, RECEIVED, PARTIAL
    }
}
