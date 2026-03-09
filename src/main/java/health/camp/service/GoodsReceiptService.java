package health.camp.service;

import health.camp.dto.goods.GoodsReceiptDto;
import health.camp.entity.goods.GoodsReceipt;
import health.camp.entity.goods.GoodsReceiptItem;
import health.camp.entity.SupplierRequest;
import health.camp.entity.SupplierRequestItem;
import health.camp.entity.MedicineLookup;
import health.camp.entity.WareHouse;
import health.camp.repository.goods.GoodsReceiptRepository;
import health.camp.repository.goods.GoodsReceiptItemRepository;
import health.camp.repository.SupplierRequestRepository;
import health.camp.repository.SupplierRequestItemRepository;
import health.camp.repository.MedicineRepository;
import health.camp.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsReceiptService {
    private final GoodsReceiptRepository goodsReceiptRepository;
    private final GoodsReceiptItemRepository goodsReceiptItemRepository;
    private final SupplierRequestRepository supplierRequestRepository;
    private final SupplierRequestItemRepository supplierRequestItemRepository;
    private final MedicineRepository medicineRepository;
    private final WareHouseRepository wareHouseRepository;

    @Transactional
    public GoodsReceiptDto createGoodsReceipt(Long requestId, GoodsReceiptDto dto) {
        SupplierRequest request = supplierRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("SupplierRequest not found: " + requestId));
        WareHouse warehouse = wareHouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found: " + dto.getWarehouseId()));

        GoodsReceipt receipt = GoodsReceipt.builder()
                .receiptNumber(dto.getReceiptNumber())
                .request(request)
                .warehouse(warehouse)
                .receivedBy(dto.getReceivedBy())
                .receivedDate(dto.getReceivedDate())
                .invoiceNumber(dto.getInvoiceNumber())
                .invoiceAmount(dto.getInvoiceAmount())
                .build();
        goodsReceiptRepository.save(receipt);

        for (GoodsReceiptDto.GoodsReceiptItemDto itemDto : dto.getItems()) {
            SupplierRequestItem requestItem = supplierRequestItemRepository.findById(itemDto.getRequestItemId())
                    .orElseThrow(() -> new RuntimeException("Request item not found: " + itemDto.getRequestItemId()));
            MedicineLookup medicine = medicineRepository.findById(itemDto.getMedicineId())
                    .orElseThrow(() -> new RuntimeException("Medicine not found: " + itemDto.getMedicineId()));

            GoodsReceiptItem item = GoodsReceiptItem.builder()
                    .receipt(receipt)
                    .requestItem(requestItem)
                    .medicine(medicine)
                    .receivedQty(itemDto.getReceivedQty())
                    .batchNumber(itemDto.getBatchNumber())
                    .expiryDate(itemDto.getExpiryDate())
                    .build();
            goodsReceiptItemRepository.save(item);

            // Update received_qty in purchase_order_items
            requestItem.setReceivedQuantity(
                (requestItem.getReceivedQuantity() == null ? 0 : requestItem.getReceivedQuantity()) + itemDto.getReceivedQty()
            );
            supplierRequestItemRepository.save(requestItem);

            // Update inventory logic (not shown, add as needed)
        }

        // Update PO status
        boolean allReceived = request.getItems().stream()
                .allMatch(i -> i.getRequestedQuantity().equals(i.getReceivedQuantity()));
        boolean anyReceived = request.getItems().stream()
                .anyMatch(i -> i.getReceivedQuantity() > 0);
        if (allReceived) {
            request.setStatus(health.camp.model.enums.Status.RECEIVED);
        } else if (anyReceived) {
            request.setStatus(health.camp.model.enums.Status.PARTIALLY_RECEIVED);
        }
        supplierRequestRepository.save(request);

        // Map response
        GoodsReceiptDto response = GoodsReceiptDto.builder()
                .id(receipt.getId())
                .receiptNumber(receipt.getReceiptNumber())
                .requestId(request.getRequestId())
                .warehouseId(warehouse.getId())
                .receivedBy(receipt.getReceivedBy())
                .receivedDate(receipt.getReceivedDate())
                .invoiceNumber(receipt.getInvoiceNumber())
                .invoiceAmount(receipt.getInvoiceAmount())
                .items(dto.getItems())
                .build();
        return response;
    }
}
