package com.train.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.train.common.exception.BusinessException;
import com.train.common.exception.BusinessExceptionEnum;
import com.train.common.resp.PageResp;
import com.train.common.util.SnowUtil;
import com.train.domain.TrainStation;
import com.train.mapper.TrainStationMapper;
import com.train.req.TrainStationQueryReq;
import com.train.req.TrainStationSaveReq;
import com.train.resp.TrainStationQueryResp;
import com.train.service.TrainStationService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 冉船银
 * @description 针对表【train_station(火车车站)】的数据库操作Service实现
 * @createDate 2023-07-29 22:11:19
 */
@Service
public class TrainStationServiceImpl extends ServiceImpl<TrainStationMapper, TrainStation>
        implements TrainStationService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainStationService.class);

    @Resource
    private TrainStationMapper trainStationMapper;

    public void save(TrainStationSaveReq req) {
        DateTime now = DateTime.now();
        TrainStation trainStation = BeanUtil.copyProperties(req, TrainStation.class);
        if (ObjectUtil.isNull(trainStation.getId())) {

            // 保存之前，先校验唯一键是否存在
            TrainStation trainStationDB = selectByUnique(req.getTrainCode(), req.getIndex());
            if (ObjectUtil.isNotEmpty(trainStationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR);
            }
            // 保存之前，先校验唯一键是否存在
            trainStationDB = selectByUnique(req.getTrainCode(), req.getName());
            if (ObjectUtil.isNotEmpty(trainStationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR);
            }

            trainStation.setId(SnowUtil.getSnowflakeNextId());
            trainStation.setCreateTime(now);
            trainStation.setUpdateTime(now);
            trainStationMapper.insert(trainStation);
        } else {
            trainStation.setUpdateTime(now);
            trainStationMapper.updateById(trainStation);
        }
    }

    private TrainStation selectByUnique(String trainCode, Integer index) {
        LambdaQueryWrapper<TrainStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainStation::getTrainCode, trainCode).eq(TrainStation::getIndex, index);
        TrainStation trainStation = trainStationMapper.selectOne(wrapper);
        if (Objects.nonNull(trainStation)) {
            return trainStation;
        } else {
            return null;
        }
    }

    private TrainStation selectByUnique(String trainCode, String name) {
        LambdaQueryWrapper<TrainStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainStation::getTrainCode, trainCode).eq(TrainStation::getName, name);
        TrainStation trainStation = trainStationMapper.selectOne(wrapper);
        if (Objects.nonNull(trainStation)) {
            return trainStation;
        } else {
            return null;
        }
    }

    public PageResp<TrainStationQueryResp> queryList(TrainStationQueryReq req) {
        LambdaQueryWrapper<TrainStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(TrainStation::getTrainCode).orderByAsc(TrainStation::getIndex);

        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            wrapper.eq(TrainStation::getTrainCode, req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainStation> trainStationList = trainStationMapper.selectList(wrapper);

        PageInfo<TrainStation> pageInfo = new PageInfo<>(trainStationList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainStationQueryResp> list = BeanUtil.copyToList(trainStationList, TrainStationQueryResp.class);

        PageResp<TrainStationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }


    public List<TrainStation> selectByTrainCode(String trainCode) {
        LambdaQueryWrapper<TrainStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainStation::getTrainCode, trainCode).orderByAsc(TrainStation::getIndex);


        return trainStationMapper.selectList(wrapper);
    }
}




