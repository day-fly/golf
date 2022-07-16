package com.druwa.user.controller;

import com.druwa.user.model.CafeUserResponse;
import com.druwa.user.services.CafeUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class CafeUserController {

    private final CafeUserService cafeUserService;

    public CafeUserController(CafeUserService cafeUserService) {
        this.cafeUserService = cafeUserService;
    }

    @GetMapping("/")
    @Operation(summary = "유저 획득", description = "최신의 유저(주민) 정보 획득")
    public CafeUserResponse getUser() {
        return cafeUserService.getCafeUser();
    }
}
