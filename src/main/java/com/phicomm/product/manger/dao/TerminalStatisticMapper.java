package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.terminal.PageWithPlatformEntity;
import com.phicomm.product.manger.model.terminal.PeriodWithPlatformEntity;
import com.phicomm.product.manger.model.terminal.StatisticEntity;
import com.phicomm.product.manger.model.terminal.TerminalCommonEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wei.yang on 2017/12/29
 */
@Repository
public interface TerminalStatisticMapper {

    /**
     * 批量写入，通常只用于首次写入
     *
     * @param terminalCommonEntities 数据
     * @param type                   类型
     */
    void groupInsert(@Param("terminalCommonEntities") List<TerminalCommonEntity> terminalCommonEntities,
                     @Param("type") String type);

    /**
     * 获取某个时间段的统计数据
     *
     * @param periodEntity 查询的时间段
     * @return 数据
     */
    List<StatisticEntity> getTimeStatisticInfo(@Param("periodEntity") PeriodWithPlatformEntity periodEntity);

    /**
     * 获取固定页码大小的数据
     *
     * @param pageEntity 查询的时间段
     * @param begin      开始
     * @return 数据
     */
    List<StatisticEntity> getPageStatisticInfo(@Param("pageEntity") PageWithPlatformEntity pageEntity,
                                               @Param("begin") int begin);
}
