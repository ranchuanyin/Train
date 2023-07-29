package com.train.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("member")
public class Member {

    private Long id;
    private String mobile;
}
