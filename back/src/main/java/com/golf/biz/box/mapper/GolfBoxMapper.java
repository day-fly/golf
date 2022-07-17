package com.golf.biz.box.mapper;

import com.golf.biz.box.entity.GolfBox;
import com.golf.biz.box.model.GolfBoxRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface GolfBoxMapper {
    ArrayList<GolfBox> getList();
    GolfBox getOneById(GolfBoxRequest golfBoxRequest);
    GolfBox getOneByUserInfo(GolfBoxRequest golfBoxRequest);
    int update(GolfBox golfBox);
}
