package com.druwa.menu.model;

public class CafeMenuResponse {
    private long id;
    private Long parentId;
    private String menuName;
    private Integer menuCost;
    private Integer menuCostEmp;
    private int menuIndex;

    public CafeMenuResponse(long id, Long parentId, String menuName, Integer menuCost, Integer menuCostEmp, int menuIndex) {
        this.id = id;
        this.parentId = parentId;
        this.menuName = menuName;
        this.menuCost = menuCost;
        this.menuCostEmp = menuCostEmp;
        this.menuIndex = menuIndex;
    }

    public long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public Integer getMenuCost() {
        return menuCost;
    }

    public Integer getMenuCostEmp() {
        return menuCostEmp;
    }

    public int getMenuIndex() {
        return menuIndex;
    }
}
