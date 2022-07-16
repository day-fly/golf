package com.golf.biz.waitUser.services;

import com.golf.biz.waitUser.entity.GolfWaitUser;
import com.golf.biz.waitUser.mapper.GolfWaitUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GolfWaitUserService {

    final GolfWaitUserMapper golfWaitUserMapper;

    public ArrayList<GolfWaitUser> getList() {
        ArrayList<GolfWaitUser> list = golfWaitUserMapper.getList();
        return list;
    }

    public void insert(GolfWaitUser golfWaitUser) {
        LocalTime now = LocalTime.now();         // 현재시간 출력
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        golfWaitUser.setWaitTime(now.format(formatter));
        golfWaitUserMapper.insert(golfWaitUser);
    }

}
