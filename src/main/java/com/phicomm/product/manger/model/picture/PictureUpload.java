package com.phicomm.product.manger.model.picture;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by xiang.zhang on 2017/9/7.
 */
public class PictureUpload {

    @JsonIgnore
    private Integer id;

    private Integer picId;

    private String picEngName;

    private String picChiName;

    private String picVersion;

    private String picUrl;

    private String picStatus;

    private Date createTime;

    private Date updateTime;

    public PictureUpload() {
    }

    public PictureUpload(Integer picId, String picEngName, String picChiName, String picVersion) {
        this.picId = picId;
        this.picEngName = picEngName;
        this.picChiName = picChiName;
        this.picVersion = picVersion;
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



}
