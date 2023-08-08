package com.train.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.train.common.context.LoginMemberContext;
import com.train.common.resp.PageResp;
import com.train.common.util.SnowUtil;
import com.train.entry.Passenger;
import com.train.mapper.PassengerMapper;
import com.train.req.PassengerQueryReq;
import com.train.req.PassengerSaveReq;
import com.train.resp.PassengerQueryResp;
import com.train.service.PassengerService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【passenger(乘车人)】的数据库操作Service实现
 * @createDate 2023-07-29 13:04:41
 */
@Service
public class PassengerServiceImpl extends ServiceImpl<PassengerMapper, Passenger> implements PassengerService {
    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);

    @Resource
    PassengerMapper passengerMapper;


    @Override
    public void savePassenger(PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        if (ObjectUtil.isNull(passenger.getId())) {
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        } else {
            passenger.setUpdateTime(now);
            passengerMapper.updateById(passenger);
        }
    }

    @Override
    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
        LambdaQueryWrapper<Passenger> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Passenger::getId);
        if (ObjectUtil.isNotNull(req.getMemberId())) {
            wrapper.eq(Passenger::getMemberId, req.getMemberId());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengerList = passengerMapper.selectList(wrapper);

        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<PassengerQueryResp> list = BeanUtil.copyToList(passengerList, PassengerQueryResp.class);

        PageResp<PassengerQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public List<PassengerQueryResp> queryMine() {
        LambdaQueryWrapper<Passenger> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Passenger::getName).eq(Passenger::getMemberId, LoginMemberContext.getId());
        List<Passenger> list = passengerMapper.selectList(wrapper);
        return BeanUtil.copyToList(list, PassengerQueryResp.class);
    }
}




