package com.huweiv.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
//@TableName("sys_role")
public class Role {

    private Long id;
    private String roleName;
    private String roleDesc;
    private Integer deleted;
    private Integer version;
}
