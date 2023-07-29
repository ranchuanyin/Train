package com.train.controller;

import com.train.common.resp.CommonResp;
import com.train.req.MemberLoginReq;
import com.train.req.MemberRegisterReq;
import com.train.req.MemberSendCodeReq;
import com.train.resp.MemberLoginResp;
import com.train.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid @RequestBody MemberSendCodeReq req) {
        memberService.sendCode(req);
        return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid @RequestBody MemberLoginReq req) {
        MemberLoginResp resp = memberService.login(req);
        return new CommonResp<>(resp);
    }
}
