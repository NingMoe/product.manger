package com.phicomm.product.manger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * properties配置文件
 * Created by yufei.liu on 2016/12/5.
 */
@Component
public class SwaggerPropertiesConfig {

    @Value("${swagger.title}")
    private String swaggerTitle;

    @Value("${swagger.service.url}")
    private String swaggerServiceUrl;

    @Value("${swagger.contact.name}")
    private String swaggerContactName;

    @Value("${swagger.contact.url}")
    private String swaggerContactUrl;

    @Value("${swagger.contact.email}")
    private String swaggerContactEmail;

    @Value("${swagger.version}")
    private String swaggerVersion;

    @Value("${swagger.scan.package}")
    private String swaggerScanPackage;

    @Value("${swagger.enabled}")
    private boolean swaggerEnabled;

    @Value("${swagger.login.name}")
    private String swaggerLoginName;

    @Value("${swagger.login.password}")
    private String swaggerLoginPassword;

    public String getSwaggerTitle() {
        return swaggerTitle;
    }

    public void setSwaggerTitle(String swaggerTitle) {
        this.swaggerTitle = swaggerTitle;
    }

    public String getSwaggerServiceUrl() {
        return swaggerServiceUrl;
    }

    public void setSwaggerServiceUrl(String swaggerServiceUrl) {
        this.swaggerServiceUrl = swaggerServiceUrl;
    }

    public String getSwaggerContactName() {
        return swaggerContactName;
    }

    public void setSwaggerContactName(String swaggerContactName) {
        this.swaggerContactName = swaggerContactName;
    }

    public String getSwaggerContactUrl() {
        return swaggerContactUrl;
    }

    public void setSwaggerContactUrl(String swaggerContactUrl) {
        this.swaggerContactUrl = swaggerContactUrl;
    }

    public String getSwaggerContactEmail() {
        return swaggerContactEmail;
    }

    public void setSwaggerContactEmail(String swaggerContactEmail) {
        this.swaggerContactEmail = swaggerContactEmail;
    }

    public String getSwaggerVersion() {
        return swaggerVersion;
    }

    public void setSwaggerVersion(String swaggerVersion) {
        this.swaggerVersion = swaggerVersion;
    }

    public boolean getSwaggerEnabled() {
        return swaggerEnabled;
    }

    public String getSwaggerScanPackage() {
        return swaggerScanPackage;
    }

    public void setSwaggerScanPackage(String swaggerScanPackage) {
        this.swaggerScanPackage = swaggerScanPackage;
    }

    public boolean isSwaggerEnabled() {
        return swaggerEnabled;
    }

    public void setSwaggerEnabled(boolean swaggerEnabled) {
        this.swaggerEnabled = swaggerEnabled;
    }

    public String getSwaggerLoginName() {
        return swaggerLoginName;
    }

    public void setSwaggerLoginName(String swaggerLoginName) {
        this.swaggerLoginName = swaggerLoginName;
    }

    public String getSwaggerLoginPassword() {
        return swaggerLoginPassword;
    }

    public void setSwaggerLoginPassword(String swaggerLoginPassword) {
        this.swaggerLoginPassword = swaggerLoginPassword;
    }
}
