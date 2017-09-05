package com.phicomm.product.manger.model.statistic;

/**
 * age section
 * Created by wei.yang on 2017/9/4.
 */
public enum AgeSectionEnum {

    ZERO_SECTION("0-9"),

    TEN_SECTION("10-19"),

    TWENTY_SECTION("20-29"),

    THIRTY_SECTION("30-39"),

    FORTY_SECTION("40-49"),

    FIFTY_SECTION("50-59"),

    SIXTY_SECTION("60-69"),

    SEVENTY_SECTION("70-79"),

    EIGHTY_SECTION("80-89"),

    NINETY_SECTION("90-99"),

    HUNDRED_SECTION("100-109"),

    ABOVE_HUNDRED_SECTION("110-129"),

    ABOVE_EIGHTY_SECTION("80-129");

    private String keyName;

    AgeSectionEnum(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
