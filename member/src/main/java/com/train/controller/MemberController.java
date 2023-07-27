package com.train.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.train.entry.Member;
import com.train.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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
    public Long register(String mobile) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getMobile, mobile);
        Member one = memberService.getOne(wrapper);
        if (Objects.nonNull(one)) {
//            return one.getId();
            throw new RuntimeException("手机号已注册");
        }
        return memberService.register(mobile);
    }
}
