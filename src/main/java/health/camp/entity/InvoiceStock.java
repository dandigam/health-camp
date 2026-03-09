package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "invoice_stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InvoiceStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private MedicineLookup medicine;

    @Column(name = "hsn_no", length = 50)
    private String hsnNo;

    @Column(name = "batch_no", length = 100)
    private String batchNo;

    @Column(name = "exp_date")
    private LocalDate expDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "strength", length = 50)
    private String strength;

    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WareHouse warehouse;
}
