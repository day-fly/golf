package com.golf.biz.box.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusResponse {
    //최초예약여부 (y,n), 타석 꽉찼는지 정보(y,n), 내가 현재 사용중인지(y,n), 대기예약을 걸었는지 여부(y,n)
    private boolean isFirstBooking;

}
