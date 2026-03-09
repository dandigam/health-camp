package health.camp.entity.goods;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Entity
@Table(name = "goods_receipt_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GoodsReceiptItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private GoodsReceipt receipt;

    @ManyToOne
    @JoinColumn(name = "request_item_id", nullable = false)
    private health.camp.entity.SupplierRequestItem requestItem;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private health.camp.entity.MedicineLookup medicine;

    @Column(name = "received_qty")
    private Integer receivedQty;

    @Column(name = "batch_number", length = 100)
    private String batchNumber;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;
}
