package com.druwa.menu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cafe_menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_cost")
    private Integer menuCost;

    @Column(name = "menu_cost_emp")
    private Integer menuCostEmp;

    @Column(name = "sale_yn")
    private String saleYn;

    @Column(name = "menu_index")
    private int menuIndex;
}
