package com.train.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.entry.Passenger;
import com.train.req.PassengerQueryReq;
import com.train.req.PassengerSaveReq;
import com.train.resp.PassengerQueryResp;

/**
 * @author 冉船银
 * @description 针对表【passenger(乘车人)】的数据库操作Service
 * @createDate 2023-07-29 13:04:41
 */
public interface PassengerService extends IService<Passenger> {
    void savePassenger(PassengerSaveReq req);

    PageResp<PassengerQueryResp> queryList(PassengerQueryReq req);
}
