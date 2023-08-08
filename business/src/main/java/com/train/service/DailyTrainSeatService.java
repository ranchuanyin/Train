package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.DailyTrainSeat;
import com.train.req.DailyTrainSeatQueryReq;
import com.train.req.DailyTrainSeatSaveReq;
import com.train.req.SeatSellReq;
import com.train.resp.DailyTrainSeatQueryResp;
import com.train.resp.SeatSellResp;

import java.util.Date;
import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【daily_train_seat(每日座位)】的数据库操作Service
 * @createDate 2023-07-30 20:31:52
 */
public interface DailyTrainSeatService extends IService<DailyTrainSeat> {

    void genDaily(Date date, String code);

    void save(DailyTrainSeatSaveReq req);

    PageResp<DailyTrainSeatQueryResp> queryList(DailyTrainSeatQueryReq req);

    void delete(Long id);

    int countSeat(Date date, String trainCode, String code);

    int countSeat(Date date, String trainCode);

    List<DailyTrainSeat> selectByCarriage(Date date, String trainCode, Integer index);

    List<SeatSellResp> querySeatSell(SeatSellReq req);
}
