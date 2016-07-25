package com.luojiash.test.java8;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeApi {
    public static void main(String[] args) {
        localDateTime();
//        duration();
    }

    public static void localDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2016-06-29 16:40:33", formatter);
        System.out.println(localDateTime); // 2016-06-29T16:40:33

        LocalDateTime localDateTime1 = LocalDateTime.parse("2016-05-28 16:40:33", formatter);
        System.out.println(Duration.between(localDateTime, localDateTime1)); // PT-768H
    }

    /**
     * 闰年2/29加一年是2/28
     */
    private static void nextYear(){
        LocalDate date=LocalDate.of(2016, 2, 29);
        LocalDate next=date.plus(Period.ofYears(1));
        System.out.println(date); // 2016-02-29
        System.out.println(next); // 2017-02-28
    }

    /**
     * 日期间隔
     */
    private static void duration(){
        LocalDate date1=LocalDate.of(2016, 2, 29);
        LocalDate date2=LocalDate.of(2017, 2, 28);
        long years=date1.until(date2, ChronoUnit.YEARS);
        Period period=date1.until(date2);
        System.out.println(years); // 0
        System.out.println(period); // P11M30D
    }
}
