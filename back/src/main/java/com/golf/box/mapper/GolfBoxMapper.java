package com.golf.box.mapper;

import com.golf.box.entity.GolfBox;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface GolfBoxMapper {
    ArrayList<GolfBox> getList();
}
