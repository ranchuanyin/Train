package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.entry.Ticket;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【ticket(车票)】的数据库操作Mapper
 * @createDate 2023-08-05 15:38:52
 * @Entity com.train.entry.Ticket
 */
@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {

}




