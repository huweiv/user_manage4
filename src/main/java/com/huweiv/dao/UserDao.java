package com.huweiv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huweiv.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName Usermapper
 * @Description TODO
 * @CreateTime 2022/4/10 10:25
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from sys_user where username=#{username}")
    User selectOneByUsername(String username);

    @Select("select * from sys_user")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "email", property = "email"),
            @Result(column = "password", property = "password"),
            @Result(
                    column = "id",
                    property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.com.huweiv.mapper.RoleMapper.findRoleByUserId")
            )
    })
    List<User> findAll();

    @Update("update sys_user set email=#{email}, password=#{password}, phoneNum=#{phoneNum}, sex=#{sex}, status=#{status}, " +
            "deleted=#{deleted} where id=#{id}")
    void update(User user);

    @Insert("insert into sys_user values(#{id}, #{username}, #{email}, #{password}, #{phoneNum}, #{sex}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(User user);

    @Insert("insert into sys_user_role values(#{userId}, #{roleId})")
    int saveUserIdAndRoleId(@Param("userId") long userId, @Param("roleId") long roleId);

    @Delete("delete from sys_user_role where userId=#{userId}")
    void deleteUserIdAndRoleIdByUserId(long userId);

    @Update("update sys_user set password=#{password} where username=#{username}")
    void updateUserPwdByUsername(User user);

    List<User> selectByCondition(@Param("user") User user, @Param("roleIds") Long[] roleIds);

    int selectTotalCountByCondition(@Param("user") User user, @Param("roleIds") Long[] roleIds);

    void deleteUserIdAndRoleIdByUserIds(Long[] userId);
}
