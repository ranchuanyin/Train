package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.common.resp.PageResp;
import com.train.domain.SkToken;
import com.train.req.SkTokenQueryReq;
import com.train.req.SkTokenSaveReq;
import com.train.resp.SkTokenQueryResp;

import java.util.Date;

/**
 * @author 冉船银
 * @description 针对表【sk_token(秒杀令牌)】的数据库操作Service
 * @createDate 2023-08-07 22:15:58
 */
public interface SkTokenService extends IService<SkToken> {

    void genDaily(Date date, String code);

    boolean validSkToken(Date date, String trainCode, Long id);

    void save(SkTokenSaveReq req);

    PageResp<SkTokenQueryResp> queryList(SkTokenQueryReq req);

    void delete(Long id);
}
