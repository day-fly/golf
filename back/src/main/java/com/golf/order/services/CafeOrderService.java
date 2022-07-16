package com.golf.order.services;

import com.golf.order.entity.CafeOrder;
import com.golf.order.entity.CafeOrderProduct;
import com.golf.order.model.CafeOrderProductModel;
import com.golf.order.model.CafeOrderRequest;
import com.golf.order.model.CafeOrderResponse;
import com.golf.order.model.OrderState;
import com.golf.order.repositories.CafeOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CafeOrderService {

    private final CafeOrderRepository cafeOrderRepository;

    public CafeOrderService(CafeOrderRepository cafeOrderRepository) {
        this.cafeOrderRepository = cafeOrderRepository;
    }

    public void createOrder(CafeOrderRequest cafeOrderRequest) {
        CafeOrder cafeOrder = new CafeOrder(
                null,
                cafeOrderRequest.getOrderName(),
                cafeOrderRequest.getOrderDong(),
                cafeOrderRequest.getOrderHo(),
                null,
                OrderState.WAIT.toString(),
                null
        );

        List<CafeOrderProduct> cafeOrderProducts = new ArrayList<>();
        for (CafeOrderProductModel model : cafeOrderRequest.getOrderProducts()) {
            CafeOrderProduct cafeOrderProduct = new CafeOrderProduct(
                    null,
                    cafeOrder,
                    model.getOrderMenuId(),
                    model.getOrderMenuName(),
                    model.getOrderMenuCost(),
                    model.getOrderMenuCount()
            );
            cafeOrderProducts.add(cafeOrderProduct);
        }
        cafeOrder.setCafeOrderProductList(cafeOrderProducts);
        cafeOrderRepository.save(cafeOrder);
    }

    public List<CafeOrderResponse> getOrderList() {
        List<CafeOrderResponse> cafeOrderResponseList = new ArrayList<>();
        List<CafeOrder> orderList =
                cafeOrderRepository.findAllByOrderDateBetween(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        for (CafeOrder cafeOrder : orderList) {
            cafeOrderResponseList.add(new CafeOrderResponse(cafeOrder));
        }
        return cafeOrderResponseList;
    }

    @Transactional
    public void restoreOrderFromList(long orderId) {
        CafeOrder cafeOrder = cafeOrderRepository.findById(orderId).get();
        cafeOrder.setOrderState(OrderState.WAIT.toString());
    }

    @Transactional
    public void cancelOrderFromList(long orderId) {
        CafeOrder cafeOrder = cafeOrderRepository.findById(orderId).get();
        cafeOrder.setOrderState(OrderState.CANCEL.toString());
    }

    @Transactional
    public void completeOrderFromList(long orderId) {
        CafeOrder cafeOrder = cafeOrderRepository.findById(orderId).get();
        cafeOrder.setOrderState(OrderState.COMPLETE.toString());
    }

}
