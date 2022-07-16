package com.golf.biz.box.mapper;

import com.golf.biz.box.entity.GolfBox;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface GolfBoxMapper {
    ArrayList<GolfBox> getList();
    int update(GolfBox golfBox);
}
