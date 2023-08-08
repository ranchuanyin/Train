package com.train.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.train.common.resp.PageResp;
import com.train.common.util.SnowUtil;
import com.train.domain.DailyTrainSeat;
import com.train.domain.TrainSeat;
import com.train.domain.TrainStation;
import com.train.mapper.DailyTrainSeatMapper;
import com.train.req.DailyTrainSeatQueryReq;
import com.train.req.DailyTrainSeatSaveReq;
import com.train.req.SeatSellReq;
import com.train.resp.DailyTrainSeatQueryResp;
import com.train.resp.SeatSellResp;
import com.train.service.DailyTrainSeatService;
import com.train.service.TrainSeatService;
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
 * @description 针对表【daily_train_seat(每日座位)】的数据库操作Service实现
 * @createDate 2023-07-30 20:31:52
 */
@Service
public class DailyTrainSeatServiceImpl extends ServiceImpl<DailyTrainSeatMapper, DailyTrainSeat>
        implements DailyTrainSeatService {
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainSeatService.class);

    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;

    @Resource
    private TrainSeatService trainSeatService;

    @Resource
    private TrainStationService trainStationService;

    public void save(DailyTrainSeatSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(req, DailyTrainSeat.class);
        if (ObjectUtil.isNull(dailyTrainSeat.getId())) {
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        } else {
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.updateById(dailyTrainSeat);
        }
    }

    public PageResp<DailyTrainSeatQueryResp> queryList(DailyTrainSeatQueryReq req) {
        LambdaQueryWrapper<DailyTrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(DailyTrainSeat::getDate).orderByAsc(DailyTrainSeat::getTrainCode).orderByAsc(DailyTrainSeat::getCarriageIndex)
                .orderByAsc(DailyTrainSeat::getCarriageSeatIndex);

        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            wrapper.eq(DailyTrainSeat::getTrainCode, req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainSeat> dailyTrainSeatList = dailyTrainSeatMapper.selectList(wrapper);

        PageInfo<DailyTrainSeat> pageInfo = new PageInfo<>(dailyTrainSeatList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainSeatQueryResp> list = BeanUtil.copyToList(dailyTrainSeatList, DailyTrainSeatQueryResp.class);

        PageResp<DailyTrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainSeatMapper.deleteById(id);
    }

    @Transactional
    public void genDaily(Date date, String trainCode) {
        LOG.info("生成日期【{}】车次【{}】的座位信息开始", DateUtil.formatDate(date), trainCode);
        LambdaQueryWrapper<DailyTrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainSeat::getDate, date).eq(DailyTrainSeat::getTrainCode, trainCode);

        // 删除某日某车次的座位信息

        dailyTrainSeatMapper.delete(wrapper);

        List<TrainStation> stationList = trainStationService.selectByTrainCode(trainCode);
        String sell = StrUtil.fillBefore("", '0', stationList.size() - 1);

        // 查出某车次的所有的座位信息
        List<TrainSeat> seatList = trainSeatService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(seatList)) {
            LOG.info("该车次没有座位基础数据，生成该车次的座位信息结束");
            return;
        }

        for (TrainSeat trainSeat : seatList) {
            DateTime now = DateTime.now();
            DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(trainSeat, DailyTrainSeat.class);
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeat.setDate(date);
            dailyTrainSeat.setSell(sell);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }
        LOG.info("生成日期【{}】车次【{}】的座位信息结束", DateUtil.formatDate(date), trainCode);
    }

    public int countSeat(Date date, String trainCode) {
        return countSeat(date, trainCode, null);
    }

    public int countSeat(Date date, String trainCode, String seatType) {
        LambdaQueryWrapper<DailyTrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainSeat::getDate, date).eq(DailyTrainSeat::getTrainCode, trainCode);

        if (StrUtil.isNotBlank(seatType)) {
            wrapper.eq(DailyTrainSeat::getSeatType, seatType);
        }
        long l = dailyTrainSeatMapper.selectCount(wrapper);
        if (l == 0L) {
            return -1;
        }
        return (int) l;
    }

    public List<DailyTrainSeat> selectByCarriage(Date date, String trainCode, Integer carriageIndex) {
        LambdaQueryWrapper<DailyTrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainSeat::getDate, date).eq(DailyTrainSeat::getTrainCode, trainCode).eq(DailyTrainSeat::getCarriageIndex, carriageIndex);

        return dailyTrainSeatMapper.selectList(wrapper);
    }

    /**
     * 查询某日某车次的所有座位
     */
    public List<SeatSellResp> querySeatSell(SeatSellReq req) {
        Date date = req.getDate();
        String trainCode = req.getTrainCode();
        LOG.info("查询日期【{}】车次【{}】的座位销售信息", DateUtil.formatDate(date), trainCode);
        LambdaQueryWrapper<DailyTrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailyTrainSeat::getDate, date).eq(DailyTrainSeat::getTrainCode, trainCode)
                .orderByAsc(DailyTrainSeat::getCarriageIndex).orderByAsc(DailyTrainSeat::getCarriageSeatIndex);

        return BeanUtil.copyToList(dailyTrainSeatMapper.selectList(wrapper), SeatSellResp.class);
    }
}




