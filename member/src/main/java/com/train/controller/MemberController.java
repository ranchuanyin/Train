package com.train.controller;

import com.train.common.resp.CommonResp;
import com.train.req.MemberRegisterReq;
import com.train.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    MemberService memberService;

    @GetMapping("count")
    public Long count() {
        return memberService.getCount();
    }

    @PostMapping("/register")
    public CommonResp<Long> register(@Validated MemberRegisterReq req) {
        Long register = memberService.register(req);
        return new CommonResp<>(register);
    }
}
