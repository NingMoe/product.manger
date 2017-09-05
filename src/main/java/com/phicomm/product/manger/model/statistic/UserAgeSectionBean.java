package com.phicomm.product.manger.model.statistic;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * age & gender
 * Created by wei.yang on 2017/9/4.
 */
public class UserAgeSectionBean {

    private String type;

    private int gender;

    private int zeroSection;

    private int tenSection;

    private int twentySection;

    private int thirtySection;

    private int fortySection;

    private int fiftySection;

    private int sixtySection;

    private int seventySection;

    private int eightySection;

    private int ninetySection;

    private int hundredSection;

    private int aboveHundredSection;

    private Date createTime;

    private Date updateTime;

    public String getType() {
        return type;
    }

    public UserAgeSectionBean setType(String type) {
        this.type = type;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public UserAgeSectionBean setGender(int gender) {
        this.gender = gender;
        return this;
    }

    public int getZeroSection() {
        return zeroSection;
    }

    public UserAgeSectionBean setZeroSection(int zeroSection) {
        this.zeroSection = zeroSection;
        return this;
    }

    public int getTenSection() {
        return tenSection;
    }

    public UserAgeSectionBean setTenSection(int tenSection) {
        this.tenSection = tenSection;
        return this;
    }

    public int getTwentySection() {
        return twentySection;
    }

    public UserAgeSectionBean setTwentySection(int twentySection) {
        this.twentySection = twentySection;
        return this;
    }

    public int getThirtySection() {
        return thirtySection;
    }

    public UserAgeSectionBean setThirtySection(int thirtySection) {
        this.thirtySection = thirtySection;
        return this;
    }

    public int getFortySection() {
        return fortySection;
    }

    public UserAgeSectionBean setFortySection(int fortySection) {
        this.fortySection = fortySection;
        return this;
    }

    public int getFiftySection() {
        return fiftySection;
    }

    public UserAgeSectionBean setFiftySection(int fiftySection) {
        this.fiftySection = fiftySection;
        return this;
    }

    public int getSixtySection() {
        return sixtySection;
    }

    public UserAgeSectionBean setSixtySection(int sixtySection) {
        this.sixtySection = sixtySection;
        return this;
    }

    public int getSeventySection() {
        return seventySection;
    }

    public UserAgeSectionBean setSeventySection(int seventySection) {
        this.seventySection = seventySection;
        return this;
    }

    public int getEightySection() {
        return eightySection;
    }

    public UserAgeSectionBean setEightySection(int eightySection) {
        this.eightySection = eightySection;
        return this;
    }

    public int getNinetySection() {
        return ninetySection;
    }

    public UserAgeSectionBean setNinetySection(int ninetySection) {
        this.ninetySection = ninetySection;
        return this;
    }

    public int getHundredSection() {
        return hundredSection;
    }

    public UserAgeSectionBean setHundredSection(int hundredSection) {
        this.hundredSection = hundredSection;
        return this;
    }

    public int getAboveHundredSection() {
        return aboveHundredSection;
    }

    public UserAgeSectionBean setAboveHundredSection(int aboveHundredSection) {
        this.aboveHundredSection = aboveHundredSection;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 取值，并转为数组
     */
    public int[] toArray(UserAgeSectionBean this) {
        int[] ageSection = new int[12];
        ageSection[0] = this.getZeroSection();
        ageSection[1] = this.getTenSection();
        ageSection[2] = this.getTwentySection();
        ageSection[3] = this.getThirtySection();
        ageSection[4] = this.getFortySection();
        ageSection[5] = this.getFiftySection();
        ageSection[6] = this.getSixtySection();
        ageSection[7] = this.getSeventySection();
        ageSection[8] = this.getEightySection();
        ageSection[9] = this.getNinetySection();
        ageSection[10] = this.getHundredSection();
        ageSection[11] = this.getAboveHundredSection();
        return ageSection;
    }

    /**
     * 获取map类型数据:按需转换
     */
    public Map<String, Integer> toMap(UserAgeSectionBean this) {
        Map<String, Integer> result = new LinkedHashMap<>();
        result.put(AgeSectionEnum.ZERO_SECTION.getKeyName(), this.getZeroSection());
        result.put(AgeSectionEnum.TEN_SECTION.getKeyName(), this.getTenSection());
        result.put(AgeSectionEnum.TWENTY_SECTION.getKeyName(), this.getTwentySection());
        result.put(AgeSectionEnum.THIRTY_SECTION.getKeyName(), this.getThirtySection());
        result.put(AgeSectionEnum.FORTY_SECTION.getKeyName(), this.getFortySection());
        result.put(AgeSectionEnum.FIFTY_SECTION.getKeyName(), this.getFiftySection());
        result.put(AgeSectionEnum.SIXTY_SECTION.getKeyName(), this.getSixtySection());
        result.put(AgeSectionEnum.SEVENTY_SECTION.getKeyName(), this.getSeventySection());
        result.put(AgeSectionEnum.ABOVE_EIGHTY_SECTION.getKeyName(), this.getEightySection() + this.getNinetySection()
                + this.getHundredSection() + this.getAboveHundredSection());
        return result;
    }

    @Override
    public String toString() {
        return "UserAgeGenderBean{" +
                "type='" + type + '\'' +
                ", gender=" + gender +
                ", zeroSection=" + zeroSection +
                ", tenSection=" + tenSection +
                ", twentySection=" + twentySection +
                ", thirtySection=" + thirtySection +
                ", fortySection=" + fortySection +
                ", fiftySection=" + fiftySection +
                ", sixtySection=" + sixtySection +
                ", seventySection=" + seventySection +
                ", eightySection=" + eightySection +
                ", ninetySection=" + ninetySection +
                ", hundredSection=" + hundredSection +
                ", aboveHundredSection=" + aboveHundredSection +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
