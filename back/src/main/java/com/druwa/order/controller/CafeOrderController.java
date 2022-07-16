package com.druwa.order.controller;

import com.druwa.event.services.EventService;
import com.druwa.order.model.CafeOrderRequest;
import com.druwa.order.model.CafeOrderResponse;
import com.druwa.order.services.CafeOrderService;
import com.druwa.user.services.CafeUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "order")
public class CafeOrderController {

    private final CafeOrderService cafeOrderService;
    private final CafeUserService cafeUserService;
    private final EventService eventService;

    public CafeOrderController(CafeOrderService cafeOrderService, CafeUserService cafeUserService, EventService eventService) {
        this.cafeOrderService = cafeOrderService;
        this.cafeUserService = cafeUserService;
        this.eventService = eventService;
    }

    @PostMapping("/")
    @Operation(summary = "주문", description = "유저의 카페 주문")
    public void createOrder(@RequestBody CafeOrderRequest cafeOrderRequest) {
        cafeOrderService.createOrder(cafeOrderRequest);
        cafeUserService.clearCafeUser();

        eventService.sendToClient("NEW_ORDER");
    }

    @PostMapping("/cancel")
    @Operation(summary = "주문 중 취소", description = "주문을 중간에 취소하고 싶을 때 사용")
    public void cancelOrder() {
        // User만 리셋하면 됨
        cafeUserService.clearCafeUser();
    }

    @GetMapping("/list")
    @Operation(summary = "주문 내역", description = "카페 주문 목록")
    public List<CafeOrderResponse> getOrderList() {
        return cafeOrderService.getOrderList();
    }

    @PatchMapping("/restore/{orderId}")
    @Operation(summary = "주문 복원", description = "완료 혹은 취소처리된 주문을 대기 처리")
    public void restoreOrderFromList(@PathVariable("orderId") long orderId) {
        cafeOrderService.restoreOrderFromList(orderId);
    }

    @PatchMapping("/cancel/{orderId}")
    @Operation(summary = "들어온 주문 취소", description = "제품이나 서비스가 제공되기 전에 주문을 취소")
    public void cancelOrderFromList(@PathVariable("orderId") long orderId) {
        cafeOrderService.cancelOrderFromList(orderId);
    }

    @PatchMapping("/complete/{orderId}")
    @Operation(summary = "들어온 주문 완료", description = "주문한 제품과 서비스를 제공하고 완료처리 함")
    public void completeOrderFromList(@PathVariable("orderId") long orderId) {
        cafeOrderService.completeOrderFromList(orderId);
    }

}
