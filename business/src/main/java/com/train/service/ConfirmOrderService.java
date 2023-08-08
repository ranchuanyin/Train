package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.ConfirmOrder;
import com.train.dto.ConfirmOrderMQDto;
import com.train.req.ConfirmOrderDoReq;
import com.train.req.ConfirmOrderQueryReq;
import com.train.resp.ConfirmOrderQueryResp;

/**
 * @author 冉船银
 * @description 针对表【confirm_order(确认订单)】的数据库操作Service
 * @createDate 2023-08-01 17:14:59
 */
public interface ConfirmOrderService extends IService<ConfirmOrder> {

    Integer queryLineCount(Long id);

    void doConfirm(ConfirmOrderMQDto confirmOrderMQDto);

    Integer cancel(Long id);

    void save(ConfirmOrderDoReq req);

    PageResp<ConfirmOrderQueryResp> queryList(ConfirmOrderQueryReq req);

    void delete(Long id);
}
