package com.druwa.order.model;

public class CafeOrderProductModel {
    private Long orderMenuId;
    private String orderMenuName;
    private int orderMenuCost;
    private int orderMenuCount;

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
