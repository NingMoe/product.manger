package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.bpmstatistic.BPMMeasureBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  血压计用户测量数据统计
 * Created by yafei.hou on 2017/11/8.
 */
@Repository
public interface BloodPressureMeasureDataSizeMapper {


    //插入
    void insertMonth(@Param("bpmMeasureBean")BPMMeasureBean bpmMeasureBean);

    void insertDay(@Param("bpmMeasureBean")BPMMeasureBean bpmMeasureBean);

    void insertHour(@Param("bpmMeasureBean")BPMMeasureBean bpmMeasureBean);

    //查询
    List<BPMMeasureBean> selectMonth(@Param("showBPMMeasureNumber")int  showBPMMeasureNumber);

    List<BPMMeasureBean> selectDay(@Param("showBPMMeasureNumber")int  showBPMMeasureNumber);

    List<BPMMeasureBean> selectHour(@Param("showBPMMeasureNumber")int  showBPMMeasureNumber);



}
