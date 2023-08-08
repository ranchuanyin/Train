package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.Station;
import com.train.req.StationQueryReq;
import com.train.req.StationSaveReq;
import com.train.resp.StationQueryResp;

import java.util.List;

/**
 * @author 冉船银
 * @description 针对表【station(车站)】的数据库操作Service
 * @createDate 2023-07-29 21:01:30
 */

public interface StationService extends IService<Station> {

    void save(StationSaveReq req);

    PageResp<StationQueryResp> queryList(StationQueryReq req);

    List<StationQueryResp> queryAll();
}
