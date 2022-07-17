package com.golf.biz.boxHistory.mapper;

import com.golf.biz.boxHistory.entity.GolfBoxHistory;
import com.golf.biz.boxHistory.model.GolfBoxHistoryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface GolfBoxHistoryMapper {
    ArrayList<GolfBoxHistory> getList(GolfBoxHistoryRequest golfBoxHistoryRequest);
    GolfBoxHistory getOne(GolfBoxHistoryRequest golfBoxHistoryRequest);
    void insert(GolfBoxHistory golfBoxHistory);
}
