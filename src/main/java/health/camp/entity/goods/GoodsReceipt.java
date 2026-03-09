package health.camp.entity.goods;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Entity
@Table(name = "goods_receipts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GoodsReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_number", length = 100)
    private String receiptNumber;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private health.camp.entity.SupplierRequest request;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private health.camp.entity.WareHouse warehouse;

    @Column(name = "received_by", length = 100)
    private String receivedBy;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "invoice_number", length = 100)
    private String invoiceNumber;

    @Column(name = "invoice_amount")
    private java.math.BigDecimal invoiceAmount;
}
