package com.golf.biz.waitUser.entity;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GolfWaitUser {
    private String id;
    private String userName;
    private String userDong;
    private String userHo;
    private String waitTime;
}
