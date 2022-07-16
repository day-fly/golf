package com.golf.biz.box.services;

import com.golf.biz.box.entity.GolfBox;
import com.golf.biz.box.mapper.GolfBoxMapper;
import com.golf.biz.box.model.GolfBoxRequest;
import com.golf.biz.box.model.UserStatusResponse;
import com.golf.biz.boxHistory.entity.GolfBoxHistory;
import com.golf.biz.boxHistory.model.GolfBoxHistoryRequest;
import com.golf.biz.boxHistory.services.GolfBoxHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GolfBoxService {

    final GolfBoxMapper golfBoxMapper;
    final GolfBoxHistoryService golfBoxHistoryService;
    final ModelMapper modelMapper;

    public ArrayList<GolfBox> getList() {
        return golfBoxMapper.getList();
    }

    public void start(GolfBox golfBox) {
        LocalTime now = LocalTime.now();         // 현재시간 출력
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        golfBox.setUseYn("Y");
        golfBox.setStartTime(now.format(formatter));
        golfBox.setEndTime(null);
        golfBoxMapper.update(golfBox);
    }

    public UserStatusResponse getStatus(GolfBoxRequest golfBoxRequest) {
        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);
        golfBoxRequest.setYyyymmdd(formatedNow);

        //최초예약여부 (y,n), 타석 꽉찼는지 정보(y,n), 내가 현재 사용중인지(y,n), 대기예약을 걸었는지 여부(y,n)
        boolean isFirstBooking = true;
        boolean isFullBox = true;
        boolean isUsingBoxByUser = false;

        //최초예약여부
        GolfBoxHistory one = golfBoxHistoryService.getOne(modelMapper.map(golfBoxRequest, GolfBoxHistoryRequest.class));
        if(one != null){
            isFirstBooking = false;
        }

        //타석 꽉찼는지 정보
        ArrayList<GolfBox> list = getList();
        boolean isEmptyBox = list.stream().anyMatch(v -> "N".equals(v.getUseYn()));
        if(isEmptyBox){
            isFullBox = false;
        }

        //내가 현재 사용중인지
        //내가 대기예약을 걸었는지

        return UserStatusResponse.builder()
                .isFirstBooking(isFirstBooking)
                .build();
    }
}
