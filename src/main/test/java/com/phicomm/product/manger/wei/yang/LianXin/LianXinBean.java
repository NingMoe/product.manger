package com.phicomm.product.manger.wei.yang.LianXin;

/**
 * Created by wei.yang on 2017/8/11.
 */
public class LianXinBean {

    private int id;

    private String title;

    private String uri;

    private String cover;

    private long ts;

    private int pv;

    private String categories;

    private int nlikes;

    private int ncomments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getNlikes() {
        return nlikes;
    }

    public void setNlikes(int nlikes) {
        this.nlikes = nlikes;
    }

    public int getNcomments() {
        return ncomments;
    }

    public void setNcomments(int ncomments) {
        this.ncomments = ncomments;
    }

    @Override
    public String toString() {
        return "LianXinBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", uri='" + uri + '\'' +
                ", cover='" + cover + '\'' +
                ", ts=" + ts +
                ", pv=" + pv +
                ", categories='" + categories + '\'' +
                ", nlikes=" + nlikes +
                ", ncomments=" + ncomments +
                '}';
    }
}
