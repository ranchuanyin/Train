package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.DailyTrain;
import com.train.domain.DailyTrainTicket;
import com.train.req.DailyTrainTicketQueryReq;
import com.train.req.DailyTrainTicketSaveReq;
import com.train.resp.DailyTrainTicketQueryResp;

import java.util.Date;

/**
 * @author 冉船银
 * @description 针对表【daily_train_ticket(余票信息)】的数据库操作Service
 * @createDate 2023-08-01 12:37:46
 */
public interface DailyTrainTicketService extends IService<DailyTrainTicket> {


    void genDaily(DailyTrain dailyTrain, Date date, String code);

    PageResp<DailyTrainTicketQueryResp> queryList(DailyTrainTicketQueryReq req);

    void delete(Long id);

    void save(DailyTrainTicketSaveReq req);

    DailyTrainTicket selectByUnique(Date date, String trainCode, String start, String end);
}
