package com.golf.biz.box.services;

import com.golf.biz.box.entity.GolfBox;
import com.golf.biz.box.mapper.GolfBoxMapper;
import com.golf.biz.box.model.GolfBoxRequest;
import com.golf.biz.box.model.GolfBoxResponse;
import com.golf.biz.box.model.UserStatusResponse;
import com.golf.biz.boxHistory.entity.GolfBoxHistory;
import com.golf.biz.boxHistory.model.GolfBoxHistoryRequest;
import com.golf.biz.boxHistory.services.GolfBoxHistoryService;
import com.golf.biz.waitUser.entity.GolfWaitUser;
import com.golf.biz.waitUser.services.GolfWaitUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GolfBoxService {

    final GolfBoxMapper golfBoxMapper;
    final GolfBoxHistoryService golfBoxHistoryService;
    final GolfWaitUserService golfWaitUserService;
    final ModelMapper modelMapper;

    public ArrayList<GolfBox> getList() {
        return golfBoxMapper.getList();
    }

    public void start(GolfBoxRequest golfBoxRequest) {
        LocalTime now = LocalTime.now();         // 현재시간 출력
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

        //TODO: 65,30을 DB값으로 바꿔야함
        long plusMinutes = 65;
        if(!golfBoxRequest.isFirstBooking()){
           plusMinutes = 30;
        }

        GolfBox golfBox = modelMapper.map(golfBoxRequest, GolfBox.class);
        golfBox.setUseYn("Y");
        golfBox.setStartTime(now.format(formatter));
        golfBox.setEndTime(now.plusMinutes(plusMinutes).format(formatter));
        golfBoxMapper.update(golfBox);
    }

    public void end(GolfBoxRequest golfBoxRequest){

        GolfBox box = golfBoxMapper.getOneById(golfBoxRequest);
        LocalTime nowTime = LocalTime.now();         // 현재시간 출력
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

        //insert History
        golfBoxHistoryService.insert(
                GolfBoxHistory.builder()
                        .userName(box.getUserName())
                        .userDong(box.getUserDong())
                        .userHo(box.getUserHo())
                        .boxId(box.getId())
                        .boxStartTime(box.getStartTime())
                        .boxEndTime(nowTime.format(timeFormatter))
                        .build()
        );

        //update golf box
        GolfBox golfBox = modelMapper.map(golfBoxRequest, GolfBox.class);
        end(golfBox);
    }

    private void end(GolfBox golfBox){
        golfBox.setUserDong(null);
        golfBox.setUserHo(null);
        golfBox.setUserName(null);
        golfBox.setStartTime(null);
        golfBox.setEndTime(null);
        golfBox.setUseYn("N");
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

        Optional<GolfBox> usingUserBox = list.stream().filter(v ->
                golfBoxRequest.getUserDong().equals(v.getUserDong())
                        && golfBoxRequest.getUserHo().equals(v.getUserHo())
                        && golfBoxRequest.getUserName().equals(v.getUserName())
        ).findFirst();

        boolean isUsingUser = usingUserBox.isEmpty() ? false : true;
        String usingUserBoxId = "";

        if(isUsingUser){
            usingUserBoxId = usingUserBox.get().getId();
        }

        ArrayList<GolfWaitUser> waitingList = golfWaitUserService.getList();

        //내가 대기예약을 걸었는지
        boolean isWaitingUser = waitingList.stream().anyMatch(v ->
                        golfBoxRequest.getUserDong().equals(v.getUserDong())
                                && golfBoxRequest.getUserHo().equals(v.getUserHo())
                                && golfBoxRequest.getUserName().equals(v.getUserName())
        );

        return UserStatusResponse.builder()
                .isFirstBooking(isFirstBooking)
                .isFullBox(isFullBox)
                .isUsingUser(isUsingUser)
                .isWaitingUser(isWaitingUser)
                .isExistWaitingUser(waitingList != null && waitingList.size() > 0)
                .usingUserBoxId(usingUserBoxId)
                .build();
    }

    public void extend(GolfBoxRequest golfBoxRequest) {
        GolfBox one = golfBoxMapper.getOneByUserInfo(golfBoxRequest);

        //30분 연장
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        one.setEndTime(LocalTime.parse(one.getEndTime(), formatter).plusMinutes(30).format(formatter));
        golfBoxMapper.update(one);
    }

    public void move(GolfBoxRequest golfBoxRequest) {
        //이동하는 타석에 원래 타석 정보 update
        GolfBox box = golfBoxMapper.getOneById(golfBoxRequest);
        golfBoxMapper.update(
                GolfBox.builder()
                        .id(golfBoxRequest.getMoveId())
                        .userName(box.getUserName())
                        .userDong(box.getUserDong())
                        .userHo(box.getUserHo())
                        .startTime(box.getStartTime())
                        .endTime(box.getEndTime())
                        .useYn(box.getUseYn())
                        .build()
        );
        //원래 타석은 초기화
        end(GolfBox.builder()
                .id(golfBoxRequest.getId())
                .build());
    }

    //남은시간 10분이내, 대기자가 없는경우 연장 가능
    public boolean isExtend(GolfBoxRequest golfBoxRequest) {
        if(golfBoxRequest == null || golfBoxRequest.getUserName() == null){
            return false;
        }

        ArrayList<GolfWaitUser> waitingList = golfWaitUserService.getList();

        if(waitingList != null && waitingList.size() > 0){
            return false;
        }

        GolfBox one = golfBoxMapper.getOneByUserInfo(golfBoxRequest);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime now = LocalTime.now();
        if(!now.plusMinutes(11).isAfter(LocalTime.parse(one.getEndTime(), formatter))){
            return false;
        }
        return true;
    }
}
