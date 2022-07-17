package com.golf.biz.box.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GolfBoxRequest {
    private String id;
    private String yyyymmdd;
    private String userName;
    private String userDong;
    private String userHo;
}