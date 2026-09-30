package org.example.demo.db;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTest {

    @Test
    public void test(){
        LocalDate localDate = LocalDate.of(2020, 1, 1);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        java.time.Instant instant = localDateTime.atZone(ZoneId.of("UTC")).toInstant();
        System.out.println(instant);
    }
}
