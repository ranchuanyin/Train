package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.DailyTrainCarriage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【daily_train_carriage(每日车厢)】的数据库操作Mapper
 * @createDate 2023-07-30 20:31:45
 * @Entity com.train.domain.DailyTrainCarriage
 */
@Mapper
public interface DailyTrainCarriageMapper extends BaseMapper<DailyTrainCarriage> {

}




