package com.druwa.event.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EventService {

    private SseEmitter emitter = new SseEmitter(-1L);

    public SseEmitter subscribe() {
        sendToClient("EVENT_CONNECTED");
        return emitter;
    }

    public void sendToClient(Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .name("sse")
                    .data(data));
        } catch (IOException e) {
            System.out.println("EVENT_CONNECTION_ERROR");
            emitter = new SseEmitter(-1L);
            sendToClient(data);
            //throw new RuntimeException("연결 오류!");
        }
    }
}
