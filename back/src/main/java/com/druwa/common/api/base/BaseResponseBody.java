package com.druwa.common.api.base;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Builder
@Data
public class BaseResponseBody<P> {
    int status;
    String message;
    P payload;

    public static <P> ResponseEntity create(
            int status, String message, P payload
    ) {
        return ResponseEntity.ok().body(
                BaseResponseBody.builder()
                        .status(status)
                        .message(message)
                        .payload(payload)
                        .build()
        );
    }
}
