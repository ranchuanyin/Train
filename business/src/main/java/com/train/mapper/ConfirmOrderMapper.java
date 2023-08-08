package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.ConfirmOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【confirm_order(确认订单)】的数据库操作Mapper
 * @createDate 2023-08-01 17:14:59
 * @Entity com.train.domain.ConfirmOrder
 */
@Mapper
public interface ConfirmOrderMapper extends BaseMapper<ConfirmOrder> {

}




