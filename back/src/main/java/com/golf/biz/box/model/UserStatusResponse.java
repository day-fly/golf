package com.golf.biz.box.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusResponse {
    private boolean isFirstBooking; // 오늘 최초예약인지 체크
    private boolean isFullBox; // 비어있는 타석이 있는지 체크
    private boolean isUsingUser; //현재 사용중인 USER 인지 체크
    private boolean isWatingUser; //현재 대기중인 USER 인지 체크
}
