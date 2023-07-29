package com.train.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.business.domain.Station;
import com.train.business.req.StationQueryReq;
import com.train.business.req.StationSaveReq;
import com.train.business.resp.StationQueryResp;
import com.train.common.resp.PageResp;

/**
 * @author 冉船银
 * @description 针对表【station(车站)】的数据库操作Service
 * @createDate 2023-07-29 21:01:30
 */

public interface StationService extends IService<Station> {

    void save(StationSaveReq req);

    PageResp<StationQueryResp> queryList(StationQueryReq req);

}
