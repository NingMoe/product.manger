package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.server.BalanceServerAddressBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * balance_server_address_info
 * Created by wei.yang on 2017/4/7.
 */
@Repository
public interface OtaServerAddressMapper {

    /**
     * 获取服务器地址
     *
     * @return 服务器地址
     */
    List<BalanceServerAddressBean> obtainServerAddress();

    /**
     * 录入ota 服务地址
     *
     * @param hostAndPort host 和 port
     * @return 是否成功
     */
    int insertServerAddress(@Param("hostAndPort") String hostAndPort);

    /**
     * 删除不用的ip
     *
     * @param hostAndPort host 和 port
     * @return 是否删除成功
     */
    int deleteServerAddress(@Param("hostAndPort") String hostAndPort);
}
