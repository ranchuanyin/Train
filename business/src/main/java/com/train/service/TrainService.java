package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.Train;
import com.train.req.TrainQueryReq;
import com.train.req.TrainSaveReq;
import com.train.resp.TrainQueryResp;

import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【train(车次)】的数据库操作Service
 * @createDate 2023-07-29 21:42:12
 */
public interface TrainService extends IService<Train> {
    void save(TrainSaveReq req);

    PageResp<TrainQueryResp> queryList(TrainQueryReq req);

    List<TrainQueryResp> queryAll();

    List<Train> selectAll();
}
