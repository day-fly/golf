package com.golf.biz.boxHistory.entity;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GolfBoxHistory {
    private String id;
    private String yyyymmdd;
    private String userName;
    private String userDong;
    private String userHo;
    private String boxId;
    private String boxStartTime;
    private String boxEndTime;
}
