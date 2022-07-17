package com.golf.biz.waitUser.mapper;

import com.golf.biz.waitUser.entity.GolfWaitUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface GolfWaitUserMapper {
    ArrayList<GolfWaitUser> getList();
    void insert(GolfWaitUser golfWaitUser);
    void cancel(GolfWaitUser golfWaitUser);
}
