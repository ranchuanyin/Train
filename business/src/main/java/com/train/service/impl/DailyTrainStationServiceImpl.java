package com.train.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.train.common.resp.PageResp;
import com.train.common.util.SnowUtil;
import com.train.domain.DailyTrainStation;
import com.train.domain.TrainStation;
import com.train.mapper.DailyTrainStationMapper;
import com.train.req.DailyTrainStationQueryReq;
import com.train.req.DailyTrainStationSaveReq;
import com.train.resp.DailyTrainStationQueryResp;
import com.train.service.DailyTrainStationService;
import com.train.service.TrainStationService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【daily_train_station(每日车站)】的数据库操作Service实现
 * @createDate 2023-07-30 20:31:58
 */
@Service
public class DailyTrainStationServiceImpl extends ServiceImpl<DailyTrainStationMapper, DailyTrainStation>
        implements DailyTrainStationService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainStationService.class);

    @Resource
    private DailyTrainStationMapper dailyTrainStationMapper;

    @Resource
    private TrainStationService trainStationService;

    public void save(DailyTrainStationSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainStation dailyTrainStation = BeanUtil.copyProperties(req, DailyTrainStation.class);
        if (ObjectUtil.isNull(dailyTrainStation.getId())) {
            dailyTrainStation.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainStation.setCreateTime(now);
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.insert(dailyTrainStation);
        } else {
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStationMapper.updateById(dailyTrainStation);
        }
    }

    public PageResp<DailyTrainStationQueryResp> queryList(DailyTrainStationQueryReq req) {
        LambdaQueryWrapper<DailyTrainStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(DailyTrainStation::getDate).orderByAsc(DailyTrainStation::getTrainCode).orderByAsc(DailyTrainStation::getIndex);
        if (ObjUtil.isNotNull(req.getDate())) {
            wrapper.eq(DailyTrainStation::getDate, req.getDate());
        }
        if (ObjUtil.isNotEmpty(req.getTrainCode())) {
            wrapper.eq(DailyTrainStation::getTrainCode, req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainStation> dailyTrainStationList = dailyTrainStationMapper.selectList(wrapper);

        PageInfo<DailyTrainStation> pageInfo = new PageInfo<>(dailyTrainStationList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainStationQueryResp> list = BeanUtil.copyToList(dailyTrainStationList, DailyTrainStationQueryResp.class);

        PageResp<DailyTrainStationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainStationMapper.deleteById(id);
    }

    @Transactional
    public void genDaily(Date date, String trainCode) {
        LOG.info("生成日期【{}】车次【{}】的车站信息开始", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的车站信息
        LambdaQueryWrapper<DailyTrainStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainStation::getDate, date).eq(DailyTrainStation::getTrainCode, trainCode);


        dailyTrainStationMapper.delete(wrapper);

        // 查出某车次的所有的车站信息
        List<TrainStation> stationList = trainStationService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(stationList)) {
            LOG.info("该车次没有车站基础数据，生成该车次的车站信息结束");
            return;
        }

        for (TrainStation trainStation : stationList) {
            DateTime now = DateTime.now();
            DailyTrainStation dailyTrainStation = BeanUtil.copyProperties(trainStation, DailyTrainStation.class);
            dailyTrainStation.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainStation.setCreateTime(now);
            dailyTrainStation.setUpdateTime(now);
            dailyTrainStation.setDate(date);
            dailyTrainStationMapper.insert(dailyTrainStation);
        }
        LOG.info("生成日期【{}】车次【{}】的车站信息结束", DateUtil.formatDate(date), trainCode);
    }

    /**
     * 按车次查询全部车站
     */
    public long countByTrainCode(Date date, String trainCode) {
        LambdaQueryWrapper<DailyTrainStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainStation::getDate, date).eq(DailyTrainStation::getTrainCode, trainCode);

        return dailyTrainStationMapper.selectCount(wrapper);
    }

    /**
     * 按车次日期查询车站列表，用于界面显示一列车经过的车站
     */
    public List<DailyTrainStationQueryResp> queryByTrain(Date date, String trainCode) {
        LambdaQueryWrapper<DailyTrainStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainStation::getDate, date).eq(DailyTrainStation::getTrainCode, trainCode).orderByAsc(DailyTrainStation::getIndex);

        List<DailyTrainStation> list = dailyTrainStationMapper.selectList(wrapper);
        return BeanUtil.copyToList(list, DailyTrainStationQueryResp.class);
    }
}




