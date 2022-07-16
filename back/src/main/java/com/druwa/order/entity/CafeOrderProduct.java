package com.druwa.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="cafe_order_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeOrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_order_id")
    private CafeOrder cafeOrder;

    @Column(name = "order_menu_id")
    private Long orderMenuId;

    @Column(name = "order_menu_name")
    private String orderMenuName;

    @Column(name = "order_menu_cost")
    private int orderMenuCost;

    @Column(name = "order_menu_count")
    private int orderMenuCount;

    @Override
    public String toString() {
        return "CafeOrderProduct{" +
                "id=" + id +
                ", cafeOrder=" + cafeOrder +
                ", orderMenuId='" + orderMenuId + '\'' +
                ", orderMenuName='" + orderMenuName + '\'' +
                ", orderMenuCost='" + orderMenuCost + '\'' +
                ", orderMenuCount='" + orderMenuCount + '\'' +
                '}';
    }
}
