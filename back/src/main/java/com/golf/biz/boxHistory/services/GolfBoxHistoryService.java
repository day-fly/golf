package com.golf.biz.boxHistory.services;

import com.golf.biz.boxHistory.entity.GolfBoxHistory;
import com.golf.biz.boxHistory.mapper.GolfBoxHistoryMapper;
import com.golf.biz.boxHistory.model.GolfBoxHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GolfBoxHistoryService {

    final GolfBoxHistoryMapper golfBoxHistoryMapper;

    public ArrayList<GolfBoxHistory> getList(GolfBoxHistoryRequest golfBoxHistoryRequest) {
        ArrayList<GolfBoxHistory> list = golfBoxHistoryMapper.getList(golfBoxHistoryRequest);
        return list;
    }

    public GolfBoxHistory getOne(GolfBoxHistoryRequest golfBoxHistoryRequest){
        return golfBoxHistoryMapper.getOne(golfBoxHistoryRequest);
    }

    public void insert(GolfBoxHistory golfBoxHistory) {
        golfBoxHistoryMapper.update(golfBoxHistory);
    }

}
