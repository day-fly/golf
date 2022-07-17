package com.golf.biz.box.controller;

import com.golf.biz.box.entity.GolfBox;
import com.golf.biz.box.model.GolfBoxRequest;
import com.golf.biz.box.model.GolfBoxResponse;
import com.golf.biz.box.model.UserStatusResponse;
import com.golf.biz.box.services.GolfBoxService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "box")
@RequiredArgsConstructor
public class GolfBoxController {

    final private GolfBoxService golfBoxService;
    final private ModelMapper modelMapper;

    @GetMapping("/list")
    @Operation(summary = "골프 타석 리스트", description = "모든 골프 타석 리스트 정보를 반환한다.")
    public List<GolfBoxResponse> getList() {
        ArrayList<GolfBox> list = golfBoxService.getList();
        return list.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/status")
    @Operation(summary = "상태 조회", description = "상태조회")
    public UserStatusResponse getStatus(@RequestBody GolfBoxRequest golfBoxRequest) {
        return golfBoxService.getStatus(golfBoxRequest);
    }

    @PostMapping("/start")
    @Operation(summary = "골프 타석 시작")
    public void start(@RequestBody GolfBoxRequest golfBoxRequest) {
        golfBoxService.start(golfBoxRequest);
    }

    @PostMapping("/end")
    @Operation(summary = "골프 종료")
    public void end(@RequestBody GolfBoxRequest golfBoxRequest) {
        golfBoxService.end(golfBoxRequest);
    }

    private GolfBoxResponse convertToDto(GolfBox golfBox) {
        GolfBoxResponse golfBoxResponse = modelMapper.map(golfBox, GolfBoxResponse.class);
        return golfBoxResponse;
    }


}
