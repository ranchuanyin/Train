package com.train.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.train.common.resp.PageResp;
import com.train.common.util.SnowUtil;
import com.train.domain.TrainCarriage;
import com.train.domain.TrainSeat;
import com.train.enums.SeatColEnum;
import com.train.mapper.TrainSeatMapper;
import com.train.req.TrainSeatQueryReq;
import com.train.req.TrainSeatSaveReq;
import com.train.resp.TrainSeatQueryResp;
import com.train.service.TrainCarriageService;
import com.train.service.TrainSeatService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【train_seat(座位)】的数据库操作Service实现
 * @createDate 2023-07-30 11:48:41
 */
@Service
public class TrainSeatServiceImpl extends ServiceImpl<TrainSeatMapper, TrainSeat>
        implements TrainSeatService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainSeatService.class);

    @Resource
    private TrainSeatMapper trainSeatMapper;

    @Resource
    private TrainCarriageService trainCarriageService;

    public void save(TrainSeatSaveReq req) {
        DateTime now = DateTime.now();
        TrainSeat trainSeat = BeanUtil.copyProperties(req, TrainSeat.class);
        if (ObjectUtil.isNull(trainSeat.getId())) {
            trainSeat.setId(SnowUtil.getSnowflakeNextId());
            trainSeat.setCreateTime(now);
            trainSeat.setUpdateTime(now);
            trainSeatMapper.insert(trainSeat);
        } else {
            trainSeat.setUpdateTime(now);
            trainSeatMapper.updateById(trainSeat);
        }
    }

    public PageResp<TrainSeatQueryResp> queryList(TrainSeatQueryReq req) {
        LambdaQueryWrapper<TrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(TrainSeat::getTrainCode).orderByAsc(TrainSeat::getCarriageIndex).orderByAsc(TrainSeat::getCarriageSeatIndex);
        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            wrapper.eq(TrainSeat::getTrainCode, req.getTrainCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<TrainSeat> trainSeatList = trainSeatMapper.selectList(wrapper);

        PageInfo<TrainSeat> pageInfo = new PageInfo<>(trainSeatList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainSeatQueryResp> list = BeanUtil.copyToList(trainSeatList, TrainSeatQueryResp.class);

        PageResp<TrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }


    @Transactional
    public void genTrainSeat(String trainCode) {
        DateTime now = DateTime.now();
        LambdaQueryWrapper<TrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainSeat::getTrainCode, trainCode);

        // 清空当前车次下的所有的座位记录

        trainSeatMapper.delete(wrapper);
        LambdaQueryWrapper<TrainCarriage> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(TrainCarriage::getTrainCode, trainCode);
        // 查找当前车次下的所有的车厢
        List<TrainCarriage> carriageList = trainCarriageService.list(wrapper1);
        LOG.info("当前车次下的车厢数：{}", carriageList.size());

        // 循环生成每个车厢的座位
        for (TrainCarriage trainCarriage : carriageList) {
            // 拿到车厢数据：行数、座位类型(得到列数)
            Integer rowCount = trainCarriage.getRowCount();
            String seatType = trainCarriage.getSeatType();
            int seatIndex = 1;

            // 根据车厢的座位类型，筛选出所有的列，比如车箱类型是一等座，则筛选出columnList={ACDF}
            List<SeatColEnum> colEnumList = SeatColEnum.getColsByType(seatType);
            LOG.info("根据车厢的座位类型，筛选出所有的列：{}", colEnumList);

            // 循环行数
            for (int row = 1; row <= rowCount; row++) {
                // 循环列数
                for (SeatColEnum seatColEnum : colEnumList) {
                    // 构造座位数据并保存数据库
                    TrainSeat trainSeat = new TrainSeat();
                    trainSeat.setId(SnowUtil.getSnowflakeNextId());
                    trainSeat.setTrainCode(trainCode);
                    trainSeat.setCarriageIndex(trainCarriage.getIndex());
                    trainSeat.setRow(StrUtil.fillBefore(String.valueOf(row), '0', 2));
                    trainSeat.setCol(seatColEnum.getCode());
                    trainSeat.setSeatType(seatType);
                    trainSeat.setCarriageSeatIndex(seatIndex++);
                    trainSeat.setCreateTime(now);
                    trainSeat.setUpdateTime(now);
                    trainSeatMapper.insert(trainSeat);
                }
            }
        }
    }

    public List<TrainSeat> selectByTrainCode(String trainCode) {
        LambdaQueryWrapper<TrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainSeat::getTrainCode, trainCode).orderByAsc(TrainSeat::getId);


        return trainSeatMapper.selectList(wrapper);
    }
}




