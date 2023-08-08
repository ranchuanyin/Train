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
import com.train.domain.DailyTrainCarriage;
import com.train.domain.TrainCarriage;
import com.train.enums.SeatColEnum;
import com.train.mapper.DailyTrainCarriageMapper;
import com.train.req.DailyTrainCarriageQueryReq;
import com.train.req.DailyTrainCarriageSaveReq;
import com.train.resp.DailyTrainCarriageQueryResp;
import com.train.service.DailyTrainCarriageService;
import com.train.service.TrainCarriageService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【daily_train_carriage(每日车厢)】的数据库操作Service实现
 * @createDate 2023-07-30 20:31:45
 */
@Service
public class DailyTrainCarriageServiceImpl extends ServiceImpl<DailyTrainCarriageMapper, DailyTrainCarriage>
        implements DailyTrainCarriageService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainCarriageService.class);

    @Resource
    private DailyTrainCarriageMapper dailyTrainCarriageMapper;

    @Resource
    private TrainCarriageService trainCarriageService;

    public void save(DailyTrainCarriageSaveReq req) {
        DateTime now = DateTime.now();

        // 自动计算出列数和总座位数
        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(req.getSeatType());
        req.setColCount(seatColEnums.size());
        req.setSeatCount(req.getColCount() * req.getRowCount());

        DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(req, DailyTrainCarriage.class);
        if (ObjectUtil.isNull(dailyTrainCarriage.getId())) {
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        } else {
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.updateById(dailyTrainCarriage);
        }
    }

    public PageResp<DailyTrainCarriageQueryResp> queryList(DailyTrainCarriageQueryReq req) {
        LambdaQueryWrapper<DailyTrainCarriage> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(DailyTrainCarriage::getDate).orderByAsc(DailyTrainCarriage::getTrainCode)
                .orderByAsc(DailyTrainCarriage::getIndex);


        if (ObjUtil.isNotNull(req.getDate())) {
            wrapper.eq(DailyTrainCarriage::getDate, req.getDate());
        }
        if (ObjUtil.isNotEmpty(req.getTrainCode())) {
            wrapper.eq(DailyTrainCarriage::getTrainCode, req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainCarriage> dailyTrainCarriageList = dailyTrainCarriageMapper.selectList(wrapper);

        PageInfo<DailyTrainCarriage> pageInfo = new PageInfo<>(dailyTrainCarriageList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainCarriageQueryResp> list = BeanUtil.copyToList(dailyTrainCarriageList, DailyTrainCarriageQueryResp.class);

        PageResp<DailyTrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainCarriageMapper.deleteById(id);
    }

    @Transactional
    public void genDaily(Date date, String trainCode) {
        LOG.info("生成日期【{}】车次【{}】的车厢信息开始", DateUtil.formatDate(date), trainCode);
        LambdaQueryWrapper<DailyTrainCarriage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainCarriage::getDate, date).eq(DailyTrainCarriage::getTrainCode, trainCode);

        // 删除某日某车次的车厢信息

        dailyTrainCarriageMapper.delete(wrapper);

        // 查出某车次的所有的车厢信息
        List<TrainCarriage> carriageList = trainCarriageService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(carriageList)) {
            LOG.info("该车次没有车厢基础数据，生成该车次的车厢信息结束");
            return;
        }

        for (TrainCarriage trainCarriage : carriageList) {
            DateTime now = DateTime.now();
            DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(trainCarriage, DailyTrainCarriage.class);
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriage.setDate(date);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        }
        LOG.info("生成日期【{}】车次【{}】的车厢信息结束", DateUtil.formatDate(date), trainCode);
    }

    public List<DailyTrainCarriage> selectBySeatType(Date date, String trainCode, String seatType) {
        LambdaQueryWrapper<DailyTrainCarriage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainCarriage::getDate, date).eq(DailyTrainCarriage::getTrainCode, trainCode).eq(DailyTrainCarriage::getSeatType, seatType);

        return dailyTrainCarriageMapper.selectList(wrapper);
    }
}




