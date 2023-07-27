package com.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.train.entry.Member;
import com.train.mapper.MemberMapper;
import com.train.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Resource
    MemberMapper memberMapper;

    @Override
    public Long getCount() {
        return memberMapper.selectCount(null);
    }
}
