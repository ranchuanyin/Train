package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.Train;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【train(车次)】的数据库操作Mapper
 * @createDate 2023-07-29 21:42:12
 * @Entity com.train.domain.Train
 */
@Mapper
public interface TrainMapper extends BaseMapper<Train> {

}




