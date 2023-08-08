package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.DailyTrainTicket;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【daily_train_ticket(余票信息)】的数据库操作Mapper
 * @createDate 2023-08-01 12:37:46
 * @Entity com.train.domain.DailyTrainTicket
 */
@Mapper
public interface DailyTrainTicketMapper extends BaseMapper<DailyTrainTicket> {

}




