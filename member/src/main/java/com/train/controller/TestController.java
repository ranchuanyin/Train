package com.train.controller;

import com.train.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class TestController {

    @Resource
    MemberService memberService;

    @GetMapping("test")
    public String test() {
        return "Hallo World";
    }

    @GetMapping("count")
    public Long count() {
        return memberService.count();
    }
}
