package com.golf.biz.boxHistory.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GolfBoxHistoryRequest {
    private String yyyymmdd;
    private String userName;
    private String userDong;
    private String userHo;
}
