package ru.test.task.trafficstatistics.common;

public enum Month {

    JAN,
    FEB,
    MAR,
    APR,
    MAY,
    JUN,
    JUL,
    AUG,
    SEP,
    OCT,
    NOV,
    DEC;

    public static Month getPreceding(Month month) {
        int ordinal = month.ordinal() - 1;
        return ordinal < 0 ? JAN : values()[ordinal];
    }

    public static Month byNumber(int number) {
        return values()[number - 1];
    }

}
