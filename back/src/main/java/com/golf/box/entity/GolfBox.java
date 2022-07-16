package com.golf.box.entity;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GolfBox {
    private String golfBoxId;
    private String golfBoxName;
    private String golfBoxUseYn;
    private String boxUserId;
    private String boxStartTime;
    private String boxEndTime;
}
