package com.phicomm.product.manger.model.statistic;

/**
 * gender and age interval
 * Created by wei.yang on 2017/9/4.
 */
public class UserAgeSectionOriginalBean {

    private int sectionNum;

    private int ageSection;

    public int getSectionNum() {
        return sectionNum;
    }

    public void setSectionNum(int sectionNum) {
        this.sectionNum = sectionNum;
    }

    public int getAgeSection() {
        return ageSection;
    }

    public void setAgeSection(int ageSection) {
        this.ageSection = ageSection;
    }

    @Override
    public String toString() {
        return "UserGenderBean{" +
                "sectionNum=" + sectionNum +
                ", ageSection=" + ageSection +
                '}';
    }
}
