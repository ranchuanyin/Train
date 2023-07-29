package com.train.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.business.domain.Station;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【station(车站)】的数据库操作Mapper
 * @createDate 2023-07-29 21:01:30
 * @Entity .domain.Station
 */
@Mapper
public interface StationMapper extends BaseMapper<Station> {

}




