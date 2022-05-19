package com.huweiv.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.List;

@Data
//@TableName("sys_user")
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String phoneNum;
    @TableField(exist = false)
    private List<Role> roles;
    private Integer sex;
    private Integer status;
    private Integer deleted;
    private Integer version;

    public String getSexStr() {
        if (this.sex == 0)
            return "男";
        return "女";
    }

    public String getStatusStr() {
        if (this.status == 1)
            return "启用";
        return "禁用";
    }
}
