package com.druwa.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cafe_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "order_dong")
    private String orderDong;

    @Column(name = "order_ho")
    private String orderHo;

    @Column(name = "order_date")
    @UpdateTimestamp
    private LocalDateTime orderDate;

    @Column(name = "order_state")
    private String orderState;

    @OneToMany(mappedBy = "cafeOrder", cascade = CascadeType.ALL)
    private List<CafeOrderProduct> cafeOrderProductList;

    @Override
    public String toString() {
        return "CafeOrder{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", orderDong='" + orderDong + '\'' +
                ", orderHo='" + orderHo + '\'' +
                ", cafeOrderProductList=" + cafeOrderProductList +
                '}';
    }
}
