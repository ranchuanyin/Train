package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.DailyTrain;
import com.train.req.DailyTrainQueryReq;
import com.train.req.DailyTrainSaveReq;
import com.train.resp.DailyTrainQueryResp;

import java.util.Date;

/**
 * @author 冉船银
 * @description 针对表【daily_train(每日车次)】的数据库操作Service
 * @createDate 2023-07-30 20:31:21
 */
public interface DailyTrainService extends IService<DailyTrain> {

    PageResp<DailyTrainQueryResp> queryList(DailyTrainQueryReq req);

    void delete(Long id);

    void genDaily(Date date);

    void save(DailyTrainSaveReq req);
}
