package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.TrainCarriage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【train_carriage(火车车厢)】的数据库操作Mapper
 * @createDate 2023-07-29 22:30:07
 * @Entity com.train.domain.TrainCarriage
 */
@Mapper
public interface TrainCarriageMapper extends BaseMapper<TrainCarriage> {

}




