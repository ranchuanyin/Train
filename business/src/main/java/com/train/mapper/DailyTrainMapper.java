package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.DailyTrain;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【daily_train(每日车次)】的数据库操作Mapper
 * @createDate 2023-07-30 20:31:21
 * @Entity com.train.domain.DailyTrain
 */
@Mapper
public interface DailyTrainMapper extends BaseMapper<DailyTrain> {

}




