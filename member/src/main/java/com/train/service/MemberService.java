package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.entry.Member;
import com.train.req.MemberLoginReq;
import com.train.req.MemberRegisterReq;
import com.train.req.MemberSendCodeReq;
import com.train.resp.MemberLoginResp;

public interface MemberService extends IService<Member> {
    Long getCount();

    Long register(MemberRegisterReq req);

    void sendCode(MemberSendCodeReq req);

    MemberLoginResp login(MemberLoginReq req);
}
