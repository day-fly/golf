package com.golf.biz.box.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GolfBoxResponse {
    private String id;
    private String name;
    private String useYn;
    private String disableYn;
}

