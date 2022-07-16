package com.golf.box.controller;

import com.golf.box.entity.GolfBox;
import com.golf.box.model.GolfBoxResponse;
import com.golf.box.services.GolfBoxService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<GolfBoxResponse> getDemoList() {
        ArrayList<GolfBox> list = golfBoxService.getList();
        return list.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private GolfBoxResponse convertToDto(GolfBox golfBox) {
        GolfBoxResponse golfBoxResponse = modelMapper.map(golfBox, GolfBoxResponse.class);
        return golfBoxResponse;
    }
}
