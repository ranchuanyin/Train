package com.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.train.domain.SkToken;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冉船银
 * @description 针对表【sk_token(秒杀令牌)】的数据库操作Mapper
 * @createDate 2023-08-07 22:15:58
 * @Entity com.train.domain.SkToken
 */
@Mapper
public interface SkTokenMapper extends BaseMapper<SkToken> {

}




