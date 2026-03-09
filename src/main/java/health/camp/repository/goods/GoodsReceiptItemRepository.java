package health.camp.repository.goods;

import health.camp.entity.goods.GoodsReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsReceiptItemRepository extends JpaRepository<GoodsReceiptItem, Long> {
}
