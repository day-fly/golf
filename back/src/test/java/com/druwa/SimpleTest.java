package com.druwa;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SimpleTest {

    @Test
    public void test() {
        System.out.println("Hello World!");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                LocalDateTime.now(), ZoneId.of("Asia/Seoul")
        );
        System.out.println(zonedDateTime.toLocalDateTime());
    }

    @Test
    public void test2() {
        System.out.println(LocalDateTime.now());
    }

}
