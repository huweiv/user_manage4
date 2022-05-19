package com.huweiv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huweiv.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName RoleMapper
 * @Description TODO
 * @CreateTime 2022/4/10 10:25
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {

    @Delete("delete from sys_user_role where roleId=#{roleId}")
    void deleteUserIdAndRoleIdByRoleId(long roleId);

    void deleteUserIdAndRoleIdByRoleIds(Long[] roleId);

    @Select("select * from sys_role where roleName=#{roleName}")
    Role selectOneByRoleName(String roleName);

    @Update("update sys_role set roleDesc=#{roleDesc}, deleted=#{deleted} where id=#{id}")
    int update(Role role);
}
