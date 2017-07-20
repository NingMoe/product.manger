package com.phicomm.product.manger.model.swagger;

/**
 * Created by wei.yang on 2017/7/19.
 */
public class SwaggerTestEnvironmentBean {

    private int id;

    private String projectName;

    private String projectAddress;

    private String swaggerAddressTest;

    private String swaggerUsernameTest;

    private String swaggerPasswordTest;

    private String description;

    private String createTime;

    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getSwaggerAddressTest() {
        return swaggerAddressTest;
    }

    public void setSwaggerAddressTest(String swaggerAddressTest) {
        this.swaggerAddressTest = swaggerAddressTest;
    }

    public String getSwaggerUsernameTest() {
        return swaggerUsernameTest;
    }

    public void setSwaggerUsernameTest(String swaggerUsernameTest) {
        this.swaggerUsernameTest = swaggerUsernameTest;
    }

    public String getSwaggerPasswordTest() {
        return swaggerPasswordTest;
    }

    public void setSwaggerPasswordTest(String swaggerPasswordTest) {
        this.swaggerPasswordTest = swaggerPasswordTest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SwaggerTestEnvironmentBean{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectAddress='" + projectAddress + '\'' +
                ", swaggerAddressTest='" + swaggerAddressTest + '\'' +
                ", swaggerUsernameTest='" + swaggerUsernameTest + '\'' +
                ", swaggerPasswordTest='" + swaggerPasswordTest + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
