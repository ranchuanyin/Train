package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.req.MemberTicketReq;
import com.train.common.resp.PageResp;
import com.train.entry.Ticket;
import com.train.req.TicketQueryReq;
import com.train.resp.TicketQueryResp;

/**
 * @author 冉船银
 * @description 针对表【ticket(车票)】的数据库操作Service
 * @createDate 2023-08-05 15:38:52
 */
public interface TicketService extends IService<Ticket> {

    PageResp<TicketQueryResp> queryList(TicketQueryReq req);

    void save(MemberTicketReq req);
}
