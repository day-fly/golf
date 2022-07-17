package com.golf.biz.waitUser.controller;

import com.golf.biz.waitUser.entity.GolfWaitUser;
import com.golf.biz.waitUser.model.GolfWaitUserResponse;
import com.golf.biz.waitUser.services.GolfWaitUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "waitUser")
@RequiredArgsConstructor
public class GolfWaitUserController {

    final private GolfWaitUserService golfWaitUserService;
    final private ModelMapper modelMapper;

    @GetMapping("/list")
    @Operation(summary = "골프 대기열 리스트")
    public List<GolfWaitUserResponse> getList() {
        ArrayList<GolfWaitUser> list = golfWaitUserService.getList();
        return list.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/insert")
    @Operation(summary = "대기열 insert")
    public void insert(@RequestBody GolfWaitUser golfWaitUser) {
        golfWaitUserService.insert(golfWaitUser);
    }

    @PostMapping("/cancel")
    @Operation(summary = "대기열 insert")
    public void cancel(@RequestBody GolfWaitUser golfWaitUser) {
        golfWaitUserService.cancel(golfWaitUser);
    }

    private GolfWaitUserResponse convertToDto(GolfWaitUser golfWaitUser) {
        GolfWaitUserResponse golfWaitUserResponse = modelMapper.map(golfWaitUser, GolfWaitUserResponse.class);
        return golfWaitUserResponse;
    }
}
