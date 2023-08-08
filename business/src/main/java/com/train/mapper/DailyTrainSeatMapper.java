package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.DailyTrainSeat;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【daily_train_seat(每日座位)】的数据库操作Mapper
 * @createDate 2023-07-30 20:31:52
 * @Entity com.train.domain.DailyTrainSeat
 */
@Mapper
public interface DailyTrainSeatMapper extends BaseMapper<DailyTrainSeat> {

}




