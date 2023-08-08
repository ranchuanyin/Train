package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.DailyTrainStation;
import com.train.req.DailyTrainStationQueryReq;
import com.train.req.DailyTrainStationSaveReq;
import com.train.resp.DailyTrainStationQueryResp;

import java.util.Date;
import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【daily_train_station(每日车站)】的数据库操作Service
 * @createDate 2023-07-30 20:31:58
 */
public interface DailyTrainStationService extends IService<DailyTrainStation> {

    void save(DailyTrainStationSaveReq req);

    PageResp<DailyTrainStationQueryResp> queryList(DailyTrainStationQueryReq req);

    void delete(Long id);

    void genDaily(Date date, String code);

    List<DailyTrainStationQueryResp> queryByTrain(Date date, String trainCode);

    long countByTrainCode(Date date, String trainCode);
}
