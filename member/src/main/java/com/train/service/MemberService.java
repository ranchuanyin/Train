package com.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.train.entry.Member;
import com.train.req.MemberRegisterReq;

public interface MemberService extends IService<Member> {
    Long getCount();

    Long register(MemberRegisterReq req);
}
