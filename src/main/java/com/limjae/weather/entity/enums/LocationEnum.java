package com.limjae.weather.entity.enums;

import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LocationEnum {
    UNKNOWN(9999999999L, "-1", "-1"),
    SEOUL(1100000000L, "60", "127"),
    BUSAN(2600000000L, "98", "76"),
    DAEGU(2700000000L, "89", "90"),
    INCHEON(2800000000L, "55", "124"),
    GWANGJU(2900000000L, "58", "74"),
    DAEJEON(3000000000L, "67", "100"),
    ULSAN(3100000000L, "102", "84"),
    SEJONG(3600000000L, "66", "103"),

    GYEONGGI(4100000000L, "60", "120"),
    GANGWON(4200000000L, "73", "134"),
    CHUNGBUK(4300000000L, "69", "107"),
    CHUNGNAM(4400000000L, "68", "100"),
    JEOLBUK(4500000000L, "63", "89"),
    JEOLNAM(4600000000L, "51", "67"),
    GYEONGBUK(4700000000L, "89", "91"),
    GYEONGNAM(4800000000L, "91", "77"),

    JEJU(5000000000L, "52", "38"),
    ;

    // not use currently
    private final long code;
    private final Point p;

    private static final Map<Point, LocationEnum> BY_POINT =
            Stream.of(values()).collect(Collectors.toMap(LocationEnum::getP, Function.identity()));

    LocationEnum(long code, String x, String y) {
        this.code = code;
        this.p = new Point(x, y);
    }

    public static LocationEnum valueOfPoint(Point point) {
        return BY_POINT.getOrDefault(point, LocationEnum.UNKNOWN);
    }


    public String getX() {
        return this.p.getX();
    }

    public String getY() {
        return this.p.getY();
    }

    private Point getP() {
        return this.p;
    }

    @Getter
    public static class Point {
        String x;
        String y;

        public Point(String x, String y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            boolean isXEquals = this.x.equals(((Point) obj).getX());
            boolean isYEquals = this.y.equals(((Point) obj).getY());
            return isXEquals && isYEquals;
        }
    }
}
