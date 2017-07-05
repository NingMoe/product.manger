package com.phicomm.product.manger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * properties配置
 * <p>
 * Created by yufei.liu on 2017/7/5.
 */
@Component
public class PropertiesConfig {

    @Value("${hermes.temp.dir}")
    private String hermesTempDir;

    public String getHermesTempDir() {
        return hermesTempDir;
    }

    public void setHermesTempDir(String hermesTempDir) {
        this.hermesTempDir = hermesTempDir;
    }
}
