package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.BalanceUserManagerMapper;
import com.phicomm.product.manger.dao.UserAgeSectionMapper;
import com.phicomm.product.manger.dao.UserInfoMapper;
import com.phicomm.product.manger.model.statistic.*;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * age section
 * Created by wei.yang on 2017/9/4.
 */
@Service
public class UserAgeSectionService {

    private static final Logger logger = Logger.getLogger(UserAgeSectionService.class);

    private BalanceUserManagerMapper userManagerMapper;

    private UserAgeSectionMapper userAgeSectionMapper;

    private UserInfoMapper userInfoMapper;

    @Autowired
    public UserAgeSectionService(BalanceUserManagerMapper userManagerMapper,
                                 UserAgeSectionMapper userAgeSectionMapper,
                                 UserInfoMapper userInfoMapper) {
        this.userAgeSectionMapper = userAgeSectionMapper;
        this.userManagerMapper = userManagerMapper;
        this.userInfoMapper = userInfoMapper;
        Assert.notNull(this.userAgeSectionMapper);
        Assert.notNull(this.userManagerMapper);
        Assert.notNull(this.userInfoMapper);
    }

    /**
     * 更新缓存
     *
     * @param ageSectionRequestBean 请求数据
     * @return 相应数据
     */
    private UserAgeSectionBean obtainSection(AgeSectionRequestBean ageSectionRequestBean) {
        CustomerContextHolder.selectLocalDataSource();
        UserAgeSectionBean userAgeSectionBean = userAgeSectionMapper.obtainUserAgeSection(ageSectionRequestBean);
        CustomerContextHolder.clearDataSource();
        return userAgeSectionBean;
    }

    /**
     * 获取用户信息的年龄分布
     *
     * @return 年龄信息
     */
    public Map<String, Map> obtainUserAgeSection() {
        return obtainSectionData(TypeEnum.USER.getKeyName());
    }

    /**
     * 获取成员的年龄信息分布
     *
     * @return 可用信息
     */
    public Map<String, Map> obtainMemberAgeSection() {
        return obtainSectionData(TypeEnum.MEMBER.getKeyName());
    }

    /**
     * 获取数据
     *
     * @param type 类型
     * @return 可用数据
     */
    private Map<String, Map> obtainSectionData(String type) {
        Map<String, Map> result = new HashMap<>();
        AgeSectionRequestBean ageSectionRequestBean = new AgeSectionRequestBean();
        ageSectionRequestBean.setType(type);
        ageSectionRequestBean.setGender(GenderEnum.BOY.getKey());
        UserAgeSectionBean boyAgeSection = obtainSection(ageSectionRequestBean);
        ageSectionRequestBean.setGender(GenderEnum.GIRL.getKey());
        UserAgeSectionBean girlAgeSection = obtainSection(ageSectionRequestBean);
        boolean invalid = boyAgeSection == null
                || girlAgeSection == null
                || LocalDate.fromDateFields(boyAgeSection.getUpdateTime()).isBefore(LocalDate.now())
                || LocalDate.fromDateFields(girlAgeSection.getUpdateTime()).isBefore(LocalDate.now());
        if (invalid) {
            logger.info("data to be updated.");
            update();
            return obtainSectionData(type);
        }
        Map boySection = boyAgeSection.toMap();
        result.put(GenderEnum.BOY.name().toLowerCase(), boySection);
        Map girlSection = girlAgeSection.toMap();
        result.put(GenderEnum.GIRL.name().toLowerCase(), girlSection);
        return result;
    }

    /**
     * 更新数据
     */
    public void update() {
        CustomerContextHolder.selectProdDataSource();
        UserAgeSectionBean boyUserAgeSection = format(userInfoMapper.obtainUserGenderInfo(GenderEnum.BOY.getKey()))
                .setType(TypeEnum.USER.getKeyName())
                .setGender(GenderEnum.BOY.getKey());
        UserAgeSectionBean girlUserAgeSection = format(userInfoMapper.obtainUserGenderInfo(GenderEnum.GIRL.getKey()))
                .setType(TypeEnum.USER.getKeyName())
                .setGender(GenderEnum.GIRL.getKey());
        UserAgeSectionBean boyMemberAgeSection = format(userManagerMapper.obtainUserGenderInfo(GenderEnum.BOY.getKey()))
                .setType(TypeEnum.MEMBER.getKeyName())
                .setGender(GenderEnum.BOY.getKey());
        UserAgeSectionBean girlMemberAgeSection = format(userManagerMapper.obtainUserGenderInfo(GenderEnum.GIRL.getKey()))
                .setType(TypeEnum.MEMBER.getKeyName())
                .setGender(GenderEnum.GIRL.getKey());
        CustomerContextHolder.clearDataSource();
        CustomerContextHolder.selectLocalDataSource();
        userAgeSectionMapper.updateAgeGenderInfo(boyUserAgeSection);
        userAgeSectionMapper.updateAgeGenderInfo(girlUserAgeSection);
        userAgeSectionMapper.updateAgeGenderInfo(boyMemberAgeSection);
        userAgeSectionMapper.updateAgeGenderInfo(girlMemberAgeSection);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 格式化数据
     *
     * @param originalBeans 初始数据
     * @return 格式化好的数据
     */
    private UserAgeSectionBean format(List<UserAgeSectionOriginalBean> originalBeans) {
        logger.info(originalBeans);
        if (originalBeans.isEmpty()) {
            return new UserAgeSectionBean();
        }
        UserAgeSectionBean ageSectionBean = new UserAgeSectionBean();
        Map<Integer, Integer> formatMap = new HashMap<>();
        originalBeans.forEach(originalBean -> formatMap.put(originalBean.getAgeSection(), originalBean.getSectionNum()));
        int aboveHundred = (formatMap.get(11) == null ? 0 : formatMap.get(11)) + (formatMap.get(12) == null ? 0 : formatMap.get(12));
        logger.info(aboveHundred);
        return ageSectionBean.setZeroSection(formatMap.get(0) == null ? 0 : formatMap.get(0))
                .setTenSection(formatMap.get(1) == null ? 0 : formatMap.get(1))
                .setTwentySection(formatMap.get(2) == null ? 0 : formatMap.get(2))
                .setThirtySection(formatMap.get(3) == null ? 0 : formatMap.get(3))
                .setFortySection(formatMap.get(4) == null ? 0 : formatMap.get(4))
                .setFiftySection(formatMap.get(5) == null ? 0 : formatMap.get(5))
                .setSixtySection(formatMap.get(6) == null ? 0 : formatMap.get(6))
                .setSeventySection(formatMap.get(7) == null ? 0 : formatMap.get(7))
                .setEightySection(formatMap.get(8) == null ? 0 : formatMap.get(8))
                .setNinetySection(formatMap.get(9) == null ? 0 : formatMap.get(9))
                .setHundredSection(formatMap.get(10) == null ? 0 : formatMap.get(10))
                .setAboveHundredSection(aboveHundred);
    }
}
