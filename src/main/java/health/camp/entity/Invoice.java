package health.camp.entity;

import health.camp.model.enums.InvoiceType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Invoice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "payment_mode", length = 50)
    private String paymentMode;

    @Column(name = "invoice_number", length = 100)
    private String invoiceNumber;

    @Column(name = "invoice_amount", precision = 12, scale = 2)
    private BigDecimal invoiceAmount;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WareHouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private InvoiceType type;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InvoiceStock> invoiceStocks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "supplier_request_id", nullable = true)
    private SupplierRequest supplierRequest; // optional link
    

    

    public void addInvoiceStock(InvoiceStock invoiceStock) {
        invoiceStocks.add(invoiceStock);
        invoiceStock.setInvoice(this);
    }

    public void removeInvoiceStock(InvoiceStock invoiceStock) {
        invoiceStocks.remove(invoiceStock);
        invoiceStock.setInvoice(null);
    }
}
