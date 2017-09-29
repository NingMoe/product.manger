package com.phicomm.product.manger.model.watchplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import scala.tools.nsc.doc.model.Public;

import java.util.Date;

/**
 * Created by xiang.zhang on 2017/9/7.
 */
public class WatchPlatePictureUpload {

    @JsonIgnore
    private Integer id;

    private Integer picId;

    private String picEngName;

    private String picChiName;

    private String picVersion;

    private String picResolution;

    private String picUrl;

    private String picStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public WatchPlatePictureUpload() {
    }

    public WatchPlatePictureUpload(Integer picId, String picEngName, String picChiName, String picVersion,String picResolution) {
        this.picId = picId;
        this.picEngName = picEngName;
        this.picChiName = picChiName;
        this.picVersion = picVersion;
        this.picResolution = picResolution;
    }

    public Integer getId() {

        return id;
    }

    public Integer getPicId() {

        return picId;
    }

    public String getPicChiName() {

        return picChiName;
    }

    public String getPicEngName() {

        return picEngName;
    }

    public String getPicVersion() {

        return picVersion;
    }
    public String getPicResolution() {

        return picResolution;
    }

    public String getPicUrl() {

        return picUrl;
    }

    public String getPicStatus() {

        return picStatus;
    }

    public Date getCreateTime() {

        return createTime;
    }

    public Date getUpdateTime() {

        return updateTime;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public void setPicId(Integer picId) {

        this.picId = picId;
    }

    public void setPicEngName(String picEngName) {

        this.picEngName = picEngName;
    }

    public void setPicChiName(String picChiName) {

        this.picChiName = picChiName;
    }

    public void setPicVersion(String picVersion) {

        this.picVersion = picVersion;
    }
    public void setPicResolution(String picResolution) {

        this.picResolution = picResolution;
    }

    public void setPicUrl(String picUrl) {

        this.picUrl = picUrl;
    }

    public void setPicStatus(String picStatus) {

        this.picStatus = picStatus;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {

        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "WatchPlatePictureUpload{" +
                "id=" + id +
                ", picId=" + picId +
                ", picEngName='" + picEngName + '\'' +
                ", picChiName='" + picChiName + '\'' +
                ", picVersion='" + picVersion + '\'' +
                ", picResolution='" + picResolution + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", picStatus='" + picStatus + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
