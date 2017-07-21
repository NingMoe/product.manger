package com.phicomm.product.manger.model.swagger;

/**
 * Created by wei.yang on 2017/7/20.
 */
public class SwaggerCoreMessageBean {

    private String projectName;

    private String projectAddress;

    private String swaggerAddressProd;

    private String swaggerUsernameProd;

    private String swaggerPasswordProd;

    private String swaggerAddressTest;

    private String swaggerUsernameTest;

    private String swaggerPasswordTest;

    private String description;

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

    public String getSwaggerAddressProd() {
        return swaggerAddressProd;
    }

    public void setSwaggerAddressProd(String swaggerAddressProd) {
        this.swaggerAddressProd = swaggerAddressProd;
    }

    public String getSwaggerUsernameProd() {
        return swaggerUsernameProd;
    }

    public void setSwaggerUsernameProd(String swaggerUsernameProd) {
        this.swaggerUsernameProd = swaggerUsernameProd;
    }

    public String getSwaggerPasswordProd() {
        return swaggerPasswordProd;
    }

    public void setSwaggerPasswordProd(String swaggerPasswordProd) {
        this.swaggerPasswordProd = swaggerPasswordProd;
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

    @Override
    public String toString() {
        return "SwaggerCoreMessageBean{" +
                "projectName='" + projectName + '\'' +
                ", projectAddress='" + projectAddress + '\'' +
                ", swaggerAddressProd='" + swaggerAddressProd + '\'' +
                ", swaggerUsernameProd='" + swaggerUsernameProd + '\'' +
                ", swaggerPasswordProd='" + swaggerPasswordProd + '\'' +
                ", swaggerAddressTest='" + swaggerAddressTest + '\'' +
                ", swaggerUsernameTest='" + swaggerUsernameTest + '\'' +
                ", swaggerPasswordTest='" + swaggerPasswordTest + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
