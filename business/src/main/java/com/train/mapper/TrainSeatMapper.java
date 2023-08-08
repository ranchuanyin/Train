package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.TrainSeat;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【train_seat(座位)】的数据库操作Mapper
 * @createDate 2023-07-30 11:48:41
 * @Entity com.train.domain.TrainSeat
 */
@Mapper
public interface TrainSeatMapper extends BaseMapper<TrainSeat> {

}




