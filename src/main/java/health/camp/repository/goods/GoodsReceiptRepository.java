package health.camp.repository.goods;

import health.camp.entity.goods.GoodsReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsReceiptRepository extends JpaRepository<GoodsReceipt, Long> {
}
