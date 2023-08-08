package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.TrainSeat;
import com.train.req.TrainSeatQueryReq;
import com.train.req.TrainSeatSaveReq;
import com.train.resp.TrainSeatQueryResp;

import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【train_seat(座位)】的数据库操作Service
 * @createDate 2023-07-30 11:48:41
 */
public interface TrainSeatService extends IService<TrainSeat> {

    void save(TrainSeatSaveReq req);

    PageResp<TrainSeatQueryResp> queryList(TrainSeatQueryReq req);

    void genTrainSeat(String trainCode);

    List<TrainSeat> selectByTrainCode(String trainCode);
}
