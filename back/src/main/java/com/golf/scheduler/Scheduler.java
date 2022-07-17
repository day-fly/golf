package com.golf.scheduler;

import com.golf.biz.box.entity.GolfBox;
import com.golf.biz.box.model.GolfBoxRequest;
import com.golf.biz.box.services.GolfBoxService;
import com.golf.biz.boxHistory.entity.GolfBoxHistory;
import com.golf.biz.boxHistory.services.GolfBoxHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class Scheduler {

    final private GolfBoxService golfBoxService;
    final private GolfBoxHistoryService golfBoxHistoryService;

    @Scheduled(fixedRate = 2000)
    public void addHistory(){
        ArrayList<GolfBox> boxList = golfBoxService.getList();

        if(boxList == null || boxList.size() == 0){
            return;
        }
        LocalTime nowTime = LocalTime.now();         // 현재시간 출력
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String yyyymmdd = nowDate.format(dateFormatter);

        //golfWaitUser.setWaitTime(now.format(formatter));

        boxList.stream().forEach(v -> {
            if("Y".equals(v.getUseYn())
                    && v.getEndTime() != null
                    && nowTime.isAfter(LocalTime.parse(v.getEndTime(), timeFormatter))){
                golfBoxHistoryService.insert(
                        GolfBoxHistory.builder()
                                .userName(v.getUserName())
                                .userDong(v.getUserDong())
                                .userHo(v.getUserHo())
                                .boxId(v.getId())
                                .boxStartTime(v.getStartTime())
                                .boxEndTime(v.getEndTime())
                                .yyyymmdd(yyyymmdd)
                                .build()
                );

                golfBoxService.end(
                        GolfBoxRequest.builder()
                                .id(v.getId())
                                .build()
                );
            }
        });

        //시간 지난 건들 히스토리로...
        //System.out.println("test   111");
    }

    @Scheduled(fixedRate = 2000)
    public void useBox(){
        //System.out.println("test   222");
    }
}
