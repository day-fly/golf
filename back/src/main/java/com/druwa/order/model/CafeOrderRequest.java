package com.druwa.order.model;

import java.util.List;

public class CafeOrderRequest {
    private String orderName;
    private String orderDong;
    private String orderHo;
    private List<CafeOrderProductModel> orderProducts;

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDong() {
        return orderDong;
    }

    public void setOrderDong(String orderDong) {
        this.orderDong = orderDong;
    }

    public String getOrderHo() {
        return orderHo;
    }

    public void setOrderHo(String orderHo) {
        this.orderHo = orderHo;
    }

    public List<CafeOrderProductModel> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<CafeOrderProductModel> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
