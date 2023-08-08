package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.DailyTrainCarriage;
import com.train.req.DailyTrainCarriageQueryReq;
import com.train.req.DailyTrainCarriageSaveReq;
import com.train.resp.DailyTrainCarriageQueryResp;

import java.util.Date;
import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【daily_train_carriage(每日车厢)】的数据库操作Service
 * @createDate 2023-07-30 20:31:45
 */
public interface DailyTrainCarriageService extends IService<DailyTrainCarriage> {

    void genDaily(Date date, String code);

    void save(DailyTrainCarriageSaveReq req);

    PageResp<DailyTrainCarriageQueryResp> queryList(DailyTrainCarriageQueryReq req);

    void delete(Long id);

    List<DailyTrainCarriage> selectBySeatType(Date date, String trainCode, String seatType);
}
