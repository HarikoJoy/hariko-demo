package com.hariko.jdk8;

import java.time.LocalDate;

public class DateDemo {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.of(2019, 12, 21);

        int month = localDate.plusDays(21).getMonthValue();
        System.out.println(month);

    }
}
