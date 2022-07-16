package com.golf.box.services;

import com.golf.box.entity.GolfBox;
import com.golf.box.mapper.GolfBoxMapper;
import com.golf.box.model.GolfBoxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GolfBoxService {

    final GolfBoxMapper golfBoxMapper;

    public ArrayList<GolfBox> getList() {
        ArrayList<GolfBox> list = golfBoxMapper.getList();
        return list;
    }

}
