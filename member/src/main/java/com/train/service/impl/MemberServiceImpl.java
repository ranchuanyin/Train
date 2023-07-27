package com.train.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.train.common.exception.BusinessException;
import com.train.common.exception.BusinessExceptionEnum;
import com.train.common.util.SnowUtil;
import com.train.entry.Member;
import com.train.mapper.MemberMapper;
import com.train.req.MemberRegisterReq;
import com.train.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Resource
    MemberMapper memberMapper;

    @Override
    public Long getCount() {
        return memberMapper.selectCount(null);
    }

    @Override
    public Long register(MemberRegisterReq req) {
        String mobile = req.getMobile();
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getMobile, mobile);
        Member one = memberMapper.selectOne(wrapper);
        if (Objects.nonNull(one)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
}
