package com.train.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.train.entry.Passenger;
import com.train.mapper.PassengerMapper;
import com.train.service.PassengerService;
import org.springframework.stereotype.Service;

/**
 * @author 冉船银
 * @description 针对表【passenger(乘车人)】的数据库操作Service实现
 * @createDate 2023-07-29 13:04:41
 */
@Service
public class PassengerServiceImpl extends ServiceImpl<PassengerMapper, Passenger> implements PassengerService {

}




