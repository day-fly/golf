package com.druwa.menu.controller;

import com.druwa.menu.model.CafeMenuResponse;
import com.druwa.menu.services.CafeMenuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "menu")
public class CafeMenuController {

    CafeMenuService cafeMenuService = null;

    public CafeMenuController(CafeMenuService cafeMenuService) {
        this.cafeMenuService = cafeMenuService;
    }

    @GetMapping("/list")
    @Operation(summary = "전체 메뉴", description = "모든 메뉴의 code와 name을 반환한다.")
    public List<CafeMenuResponse> getDemoList() {
        return cafeMenuService.getCafeMenuList();
    }
}
