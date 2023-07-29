package com.train.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.business.domain.TrainStation;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【train_station(火车车站)】的数据库操作Mapper
 * @createDate 2023-07-29 22:11:19
 * @Entity com.train.business.domain.TrainStation
 */
@Mapper
public interface TrainStationMapper extends BaseMapper<TrainStation> {

}




