package health.camp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import health.camp.model.enums.Status;

@Entity
@Table(name = "supplier_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SupplierRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "purchase_order")
    private String purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WareHouse warehouse;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private Status status;

    @Column(name = "priority")
    private Boolean priority = false;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierRequestItem> items;

    @OneToOne(mappedBy = "supplierRequest", cascade = CascadeType.ALL)
    private Invoice invoice;

}

