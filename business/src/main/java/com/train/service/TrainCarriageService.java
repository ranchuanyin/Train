package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.TrainCarriage;
import com.train.req.TrainCarriageQueryReq;
import com.train.req.TrainCarriageSaveReq;
import com.train.resp.TrainCarriageQueryResp;

import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【train_carriage(火车车厢)】的数据库操作Service
 * @createDate 2023-07-29 22:30:07
 */
public interface TrainCarriageService extends IService<TrainCarriage> {

    PageResp<TrainCarriageQueryResp> queryList(TrainCarriageQueryReq req);

    void save(TrainCarriageSaveReq req);

    List<TrainCarriage> selectByTrainCode(String trainCode);
}
