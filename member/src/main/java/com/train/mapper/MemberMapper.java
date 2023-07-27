package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.entry.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
