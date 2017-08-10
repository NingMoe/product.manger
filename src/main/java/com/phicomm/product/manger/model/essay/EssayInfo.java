package com.phicomm.product.manger.model.essay;

/**
 * 文章类
 * Created by Qiang on 2017/8/7.
 */
public class EssayInfo {

    private Long id;

    private String essayId;

    private String title;

    private String subtitle;

    private String summary;

    private String coverUrl;

    private String contentUrl;

    private String createTime;

    private String updateTime;

    public EssayInfo(String essayId, String title, String subtitle, String summary, String coverUrl, String contentUrl) {
        this.essayId = essayId;
        this.title = title;
        this.subtitle = subtitle;
        this.summary = summary;
        this.coverUrl = coverUrl;
        this.contentUrl = contentUrl;
    }

    public EssayInfo() {
    }

    public Long getId() {
        return id;
    }

    public String getEssayId() {
        return essayId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getSummary() {
        return summary;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEssayId(String essayId) {
        this.essayId = essayId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
