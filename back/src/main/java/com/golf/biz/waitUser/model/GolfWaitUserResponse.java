package com.golf.biz.waitUser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GolfWaitUserResponse {
    private String golfBoxId;
    private String golfBoxName;
    private String golfBoxUseYn;
    private String boxUserId;
    private String boxStartTime;
    private String boxEndTime;
}

