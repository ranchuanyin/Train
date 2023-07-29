package com.train.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.train.common.context.LoginMemberContext;
import com.train.common.util.SnowUtil;
import com.train.entry.Passenger;
import com.train.mapper.PassengerMapper;
import com.train.req.PassengerSaveReq;
import com.train.service.PassengerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 冉船银
 * @description 针对表【passenger(乘车人)】的数据库操作Service实现
 * @createDate 2023-07-29 13:04:41
 */
@Service
public class PassengerServiceImpl extends ServiceImpl<PassengerMapper, Passenger> implements PassengerService {

    @Resource
    PassengerMapper passengerMapper;


    @Override
    public void savePassenger(PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }
}




