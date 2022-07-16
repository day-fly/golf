package com.golf.biz.boxHistory.controller;

import com.golf.biz.boxHistory.entity.GolfBoxHistory;
import com.golf.biz.boxHistory.model.GolfBoxHistoryRequest;
import com.golf.biz.boxHistory.services.GolfBoxHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "boxHistory")
@RequiredArgsConstructor
public class GolfBoxHistoryController {

    final private GolfBoxHistoryService golfBoxHistoryService;
    final private ModelMapper modelMapper;

    @GetMapping("/list")
    @Operation(summary = "골프 타석 리스트", description = "모든 골프 타석 리스트 정보를 반환한다.")
    public List<GolfBoxHistory> getList(@RequestBody GolfBoxHistoryRequest golfBoxHistoryRequest) {
        ArrayList<GolfBoxHistory> list = golfBoxHistoryService.getList(golfBoxHistoryRequest);
        return list;
    }
}
