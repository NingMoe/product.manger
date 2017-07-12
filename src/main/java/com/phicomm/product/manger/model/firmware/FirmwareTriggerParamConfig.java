package com.phicomm.product.manger.model.firmware;

public class FirmwareTriggerParamConfig {

    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    @Override
    public String toString() {
        return "FirmwareTriggerParamConfig{" +
                "param='" + param + '\'' +
                '}';
    }
}