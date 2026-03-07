package health.camp.repository;

import health.camp.entity.InvoiceDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDocumentRepository extends JpaRepository<InvoiceDocument, Long> {

    List<InvoiceDocument> findByInvoiceId(Long invoiceId);

    void deleteByInvoiceId(Long invoiceId);
}
