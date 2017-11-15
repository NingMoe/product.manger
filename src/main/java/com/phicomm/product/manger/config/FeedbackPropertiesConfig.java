package com.phicomm.product.manger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * feedback.properties配置文件
 *
 * @author qiang.ren
 * @date 2017/11/14
 */
@Component
public class FeedbackPropertiesConfig {

    @Value("${feedback.base.url}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
