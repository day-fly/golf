package com.druwa.demo.controllers;

import com.druwa.common.api.base.BaseResponseBody;
import com.druwa.demo.Demo;
import com.druwa.demo.repositories.DemoRepositorySupport;
import com.druwa.demo.services.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "public/demo")
public class DemoController {

    private final DemoRepositorySupport demoRepositorySupport;
    private final DemoService demoService;

    @Autowired
    public DemoController(DemoRepositorySupport demoRepositorySupport, DemoService demoService) {
        this.demoRepositorySupport = demoRepositorySupport;
        this.demoService = demoService;
    }

    @GetMapping("/healthcheck")
    @Operation(summary = "샘플용 API", description = "강제로 200 만들기")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK !!"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    public ResponseEntity demo() {
        return BaseResponseBody.create(
            200,
            "success - demo",
            demoRepositorySupport.getDemoes()
        );
    }

    @PostMapping("/{name}")
    @Operation(summary = "path에 이름 넣기", description = "DB의 id를 반환해준다.")
    public long setDemo(@PathVariable("name") String name) {
        return demoService.setDemo(name);
    }

    @GetMapping("/demolist")
    @Operation(summary = "Demo list", description = "현재 demo db값을 모두 가지고 온다")
    public List<Demo> getDemoList() {
        return demoService.getDemoList();
    }

    @GetMapping("/demo")
    @Operation(summary = "", description = "현재 demo db값을 모두 가지고 온다")
    public Demo getDemoSingle(@RequestParam("name") String name) {
        return demoService.getDemoByName(name);
    }

}
