package com.golf.biz.box.entity;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GolfBox {
    private String id;
    private String name;
    private String useYn;
    private String userName;
    private String userDong;
    private String userHo;
    private String startTime;
    private String endTime;
    private String disableYn;
}
