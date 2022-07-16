package com.druwa.order.model;

import com.druwa.order.entity.CafeOrderProduct;

public class CafeOrderProductResponse {
    private Long id;
    private Long orderMenuId;
    private String orderMenuName;
    private int orderMenuCost;
    private int orderMenuCount;

    public CafeOrderProductResponse(CafeOrderProduct cafeOrderProduct) {
        this.id = cafeOrderProduct.getId();
        this.orderMenuId = cafeOrderProduct.getOrderMenuId();
        this.orderMenuName = cafeOrderProduct.getOrderMenuName();
        this.orderMenuCost = cafeOrderProduct.getOrderMenuCost();
        this.orderMenuCount = cafeOrderProduct.getOrderMenuCount();
    }

    public Long getId() {
        return id;
    }

    public Long getOrderMenuId() {
        return orderMenuId;
    }

    public String getOrderMenuName() {
        return orderMenuName;
    }

    public int getOrderMenuCost() {
        return orderMenuCost;
    }

    public int getOrderMenuCount() {
        return orderMenuCount;
    }
}
