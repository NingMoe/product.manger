package com.phicomm.product.manger.module.analysis;

import com.google.common.collect.Maps;
import com.phicomm.product.manger.dao.BalanceUserManagerMapper;
import com.phicomm.product.manger.dao.UserInfoMapper;
import com.phicomm.product.manger.model.statistic.AnalysisUserMember;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.TreeMap;

/**
 * 缓存接口的实现类
 * Created by Qiang on 2017/7/19.
 */
@Component
public class UserCacheImpl implements UserCache {

    private static final String[] USER_AGE_RANGE = new String[]{"1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90"};

    private static final String STATISTIC_USER_MEMBER = "STATISTIC_USER_MEMBER";//cacheMap的键

    private static final int cacheLife = 1000 * 60 * 15;//默认cache有效期为15分钟

    private BalanceUserManagerMapper balanceUserManagerMapper;

    private UserInfoMapper userInfoMapper;

    private Map<String, AnalysisUserMember> cacheMap = Maps.newConcurrentMap();//缓存数据

    private long activeTime;//有效期

    @Autowired
    public UserCacheImpl(BalanceUserManagerMapper balanceUserManagerMapper,
                         UserInfoMapper userInfoMapper) {
        this.balanceUserManagerMapper = balanceUserManagerMapper;
        this.userInfoMapper = userInfoMapper;
        Assert.notNull(this.balanceUserManagerMapper);
        Assert.notNull(this.userInfoMapper);
    }

    /**
     * 获取用户和成员的数据缓存
     *
     * @return 用户和成员的数据
     */
    @Override
    public AnalysisUserMember getUserCache() {
        if (expired()) {
            return cacheMap.get(STATISTIC_USER_MEMBER);
        }
        AnalysisUserMember analysisUserMember = new AnalysisUserMember(getDataSource(0), getDataSource(1));
        cacheMap.put(STATISTIC_USER_MEMBER, analysisUserMember);
        activeTime = System.currentTimeMillis() + cacheLife;
        return analysisUserMember;
    }

    /**
     * 判断userCache是否有效
     *
     * @return true:有效 false:失效
     */
    private boolean expired() {
        return null != cacheMap.get(STATISTIC_USER_MEMBER) && (System.currentTimeMillis() < activeTime);
    }

    /**
     * 从数据库读取用户或成员的信息
     *
     * @param i 0：用户 1：成员
     * @return 用户或成员的信息
     */
    private Map<String, int[]> getDataSource(int i) {
        Map<String, int[]> result = new TreeMap<>();
        int lowAge = 1;
        int highAge = 10;
        CustomerContextHolder.selectProdDataSource();
        switch (i) {
            case 0:
                for (String anAgeStr : USER_AGE_RANGE) {
                    int[] num = new int[2];
                    num[0] = userInfoMapper.statisticUserByAge(lowAge, highAge, 0);
                    num[1] = userInfoMapper.statisticUserByAge(lowAge, highAge, 1);
                    result.put(anAgeStr, num);
                    lowAge += 10;
                    highAge += 10;
                }
                break;
            case 1:
                for (String anAgeStr : USER_AGE_RANGE) {
                    int[] num = new int[2];
                    num[0] = balanceUserManagerMapper.statisticMemberByAge(lowAge, highAge, 0);
                    num[1] = balanceUserManagerMapper.statisticMemberByAge(lowAge, highAge, 1);
                    result.put(anAgeStr, num);
                    lowAge += 10;
                    highAge += 10;
                }
                break;
            default:
                break;
        }
        CustomerContextHolder.clearDataSource();
        return result;
    }
}
