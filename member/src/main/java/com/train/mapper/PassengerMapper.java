package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.entry.Passenger;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【passenger(乘车人)】的数据库操作Mapper
 * @createDate 2023-07-29 13:04:41
 * @Entity generator.domain.Passenger
 */
@Mapper
public interface PassengerMapper extends BaseMapper<Passenger> {

}




