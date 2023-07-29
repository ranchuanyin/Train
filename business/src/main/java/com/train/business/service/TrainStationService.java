package com.train.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.business.domain.TrainStation;
import com.train.business.req.TrainStationQueryReq;
import com.train.business.req.TrainStationSaveReq;
import com.train.business.resp.TrainStationQueryResp;
import com.train.common.resp.PageResp;

import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【train_station(火车车站)】的数据库操作Service
 * @createDate 2023-07-29 22:11:19
 */
public interface TrainStationService extends IService<TrainStation> {
    void save(TrainStationSaveReq req);

    PageResp<TrainStationQueryResp> queryList(TrainStationQueryReq req);

    List<TrainStation> selectByTrainCode(String trainCode);

}
