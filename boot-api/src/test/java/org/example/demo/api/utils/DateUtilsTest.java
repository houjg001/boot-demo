package org.example.demo.api.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class DateUtilsTest {


    @Test
    void formatDate(){
        System.out.println(DateUtils.formatDate(new Date()));
        System.out.println(DateUtils.formatDate(new Date(), DateUtils.DEFAULT_PATTERN));
    }

    @Test
    void parseDate(){
        System.out.println(DateUtils.parseDate("2020-03-20 23:59:59", DateUtils.DATETIME_PATTERN));
    }

    @Test
    void getValue(){
        System.out.println(DateUtils.getValue(new Date(), DateUtils.SECOND));
    }
}
