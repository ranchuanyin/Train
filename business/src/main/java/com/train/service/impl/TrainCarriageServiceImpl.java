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
import com.train.domain.TrainCarriage;
import com.train.enums.SeatColEnum;
import com.train.mapper.TrainCarriageMapper;
import com.train.req.TrainCarriageQueryReq;
import com.train.req.TrainCarriageSaveReq;
import com.train.resp.TrainCarriageQueryResp;
import com.train.service.TrainCarriageService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 冉船银
 * @description 针对表【train_carriage(火车车厢)】的数据库操作Service实现
 * @createDate 2023-07-29 22:30:07
 */
@Service
public class TrainCarriageServiceImpl extends ServiceImpl<TrainCarriageMapper, TrainCarriage>
        implements TrainCarriageService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainCarriageService.class);

    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    public void save(TrainCarriageSaveReq req) {
        DateTime now = DateTime.now();

        // 自动计算出列数和总座位数
        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(req.getSeatType());
        req.setColCount(seatColEnums.size());
        req.setSeatCount(req.getColCount() * req.getRowCount());

        TrainCarriage trainCarriage = BeanUtil.copyProperties(req, TrainCarriage.class);
        if (ObjectUtil.isNull(trainCarriage.getId())) {

            // 保存之前，先校验唯一键是否存在
            TrainCarriage trainCarriageDB = selectByUnique(req.getTrainCode(), req.getIndex());
            if (ObjectUtil.isNotEmpty(trainCarriageDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CARRIAGE_INDEX_UNIQUE_ERROR);
            }

            trainCarriage.setId(SnowUtil.getSnowflakeNextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.insert(trainCarriage);
        } else {
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.updateById(trainCarriage);
        }
    }

    private TrainCarriage selectByUnique(String trainCode, Integer index) {
        LambdaQueryWrapper<TrainCarriage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainCarriage::getTrainCode, trainCode).eq(TrainCarriage::getIndex, index);

        TrainCarriage trainCarriage = trainCarriageMapper.selectOne(wrapper);
        if (Objects.nonNull(trainCarriage)) {
            return trainCarriage;
        } else {
            return null;
        }
    }

    public PageResp<TrainCarriageQueryResp> queryList(TrainCarriageQueryReq req) {
        LambdaQueryWrapper<TrainCarriage> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(TrainCarriage::getTrainCode).orderByAsc(TrainCarriage::getIndex);


        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            wrapper.eq(TrainCarriage::getTrainCode, req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainCarriage> trainCarriageList = trainCarriageMapper.selectList(wrapper);

        PageInfo<TrainCarriage> pageInfo = new PageInfo<>(trainCarriageList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainCarriageQueryResp> list = BeanUtil.copyToList(trainCarriageList, TrainCarriageQueryResp.class);

        PageResp<TrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }


    public List<TrainCarriage> selectByTrainCode(String trainCode) {
        LambdaQueryWrapper<TrainCarriage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainCarriage::getTrainCode, trainCode).orderByAsc(TrainCarriage::getIndex);
        return trainCarriageMapper.selectList(wrapper);
    }
}




