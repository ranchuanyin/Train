package com.train.req;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class MemberRegisterReq {


    @NotBlank(message = "【手机号】不能为空")
    @Length(min = 11, max = 11, message = "【手机号】不合法")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "MemberRegisterReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
