package com.golf.biz.box.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GolfBoxResponse {
    private String golfBoxId;
    private String golfBoxName;
    private String golfBoxUseYn;
    private String boxUserId;
    private String boxStartTime;
    private String boxEndTime;
}

