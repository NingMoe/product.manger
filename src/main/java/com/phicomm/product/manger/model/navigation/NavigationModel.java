package com.phicomm.product.manger.model.navigation;

import java.util.List;

/**
 * 导航栏一条填充信息
 * Created by yufei.liu on 2017/6/5.
 */
public class NavigationModel {

    private String id;

    private String titleName;

    private String titleDescription;

    private String directoryIcon;

    private String directoryName;

    private String directoryUrl;

    private List<String> subDirectories;

    public NavigationModel() {
    }

    public NavigationModel(String id,
                           String titleName,
                           String titleDescription,
                           String directoryIcon,
                           String directoryName,
                           String directoryUrl,
                           List<String> subDirectories) {
        this.id = id;
        this.titleName = titleName;
        this.titleDescription = titleDescription;
        this.directoryIcon = directoryIcon;
        this.directoryName = directoryName;
        this.directoryUrl = directoryUrl;
        this.subDirectories = subDirectories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleDescription() {
        return titleDescription;
    }

    public void setTitleDescription(String titleDescription) {
        this.titleDescription = titleDescription;
    }

    public String getDirectoryIcon() {
        return directoryIcon;
    }

    public void setDirectoryIcon(String directoryIcon) {
        this.directoryIcon = directoryIcon;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryUrl() {
        return directoryUrl;
    }

    public void setDirectoryUrl(String directoryUrl) {
        this.directoryUrl = directoryUrl;
    }

    public List<String> getSubDirectories() {
        return subDirectories;
    }

    public void setSubDirectories(List<String> subDirectories) {
        this.subDirectories = subDirectories;
    }

    @Override
    public String toString() {
        return "NavigationModel{" +
                "id='" + id + '\'' +
                ", titleName='" + titleName + '\'' +
                ", titleDescription='" + titleDescription + '\'' +
                ", directoryIcon='" + directoryIcon + '\'' +
                ", directoryName='" + directoryName + '\'' +
                ", directoryUrl='" + directoryUrl + '\'' +
                ", subDirectories=" + subDirectories +
                '}';
    }
}
