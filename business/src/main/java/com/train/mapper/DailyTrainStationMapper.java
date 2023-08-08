package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.DailyTrainStation;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【daily_train_station(每日车站)】的数据库操作Mapper
 * @createDate 2023-07-30 20:31:58
 * @Entity com.train.domain.DailyTrainStation
 */
@Mapper
public interface DailyTrainStationMapper extends BaseMapper<DailyTrainStation> {

}




