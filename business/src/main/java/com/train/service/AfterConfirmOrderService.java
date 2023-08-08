package com.train.service;


import com.train.domain.ConfirmOrder;
import com.train.domain.DailyTrainSeat;
import com.train.domain.DailyTrainTicket;
import com.train.req.ConfirmOrderTicketReq;

import java.util.List;

public interface AfterConfirmOrderService {

    void afterDoConfirm(DailyTrainTicket dailyTrainTicket, List<DailyTrainSeat> finalSeatList, List<ConfirmOrderTicketReq> tickets, ConfirmOrder confirmOrder);
}
