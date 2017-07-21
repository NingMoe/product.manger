package com.phicomm.product.manger.model.statistic;

import java.util.Map;

/**
 * 缓存的用户和成员信息
 * Created by Qiang on 2017/7/19.
 */
public class AnalysisUserMember {

    private Map<String, int[]> userResult;

    private Map<String, int[]> memberResult;

    public Map<String, int[]> getUserResult() {
        return userResult;
    }

    public Map<String, int[]> getMemberResult() {
        return memberResult;
    }

    public AnalysisUserMember(Map<String, int[]> userResult, Map<String, int[]> memberResult) {
        this.userResult = userResult;
        this.memberResult = memberResult;
    }
}
