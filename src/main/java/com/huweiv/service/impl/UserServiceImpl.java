package com.huweiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huweiv.domain.PageBean;
import com.huweiv.domain.Result;
import com.huweiv.domain.Role;
import com.huweiv.domain.User;
import com.huweiv.dao.UserDao;
import com.huweiv.service.UserService;
import com.huweiv.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName UserServiceImpl
 * @Description TODO
 * @CreateTime 2022/4/10 10:28
 */
@Service("userService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Resource(name = "bCryptPasswordEncoder")
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PageBean<User> list(User user, Long[] roleIds, int currentPage, int pageSize) {
        if (user.getUsername() != null && user.getUsername().length() > 0)
            user.setUsername("%" + user.getUsername() + "%");
        if (user.getEmail() != null && user.getEmail().length() > 0)
            user.setEmail("%" + user.getEmail() + "%");
        if (user.getPhoneNum() != null && user.getPhoneNum().length() > 0)
            user.setPhoneNum("%" + user.getPhoneNum() + "%");
        int totalCount = userDao.selectTotalCountByCondition(user, roleIds);
        List<User> userList = userDao.selectByCondition(user, roleIds);
        List<User> userListResult = new ArrayList<>();
        int start = (currentPage - 1) * pageSize;
        int stop = currentPage * pageSize;
        if (userList.size() == 0 || start >= userList.size())
            userListResult = null;
        else if (start == userList.size() - 1)
            userListResult.add(userList.get(userList.size() - 1));
        else {
            if (stop >= userList.size())
                stop = userList.size();
            userListResult = userList.subList(start, stop);
        }
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setRows(userListResult);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public Result add(User user, Long[] roleIds) {
        boolean flag = false;
        User userInfo = userDao.selectOneByUsername(user.getUsername());
        if (null != userInfo && userInfo.getDeleted() == 0)
            return new Result(Code.SAVE_ERR, "用户名已被注册");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (null == userInfo)
            userDao.insert(user);
        else {
            user.setId(userInfo.getId());
            user.setDeleted(0);
            userDao.update(user);
        }
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                flag = userDao.saveUserIdAndRoleId(user.getId(), roleId) > 0;
                if (!flag)
                    break;
            }
        }
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "添加成功" : "添加失败";
        return new Result(code, msg);
    }

    @Override
    public Result edit(User user, Long[] roleIds, int isUpdateUserName, int isUpdateUserPwd) {
        if(isUpdateUserName == 0)
            user.setUsername(null);
        else {
            User userInfo = userDao.selectOneByUsername(user.getUsername());
            if (null != userInfo)
                return new Result(Code.UPDATE_ERR, "角色名已被注册");
        }
        if (isUpdateUserPwd == 1)
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.updateById(user);
        boolean flag = true;
        if (roleIds != null) {
            userDao.deleteUserIdAndRoleIdByUserId(user.getId());
            for (Long roleId : roleIds) {
                flag = userDao.saveUserIdAndRoleId(user.getId(), roleId) > 0;
                if (!flag)
                    break;
            }
        }
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "编辑成功" : "编辑失败";
        return new Result(code, msg);
    }

    @Override
    public Result batchDelete(Long[] userIds) {
        userDao.deleteUserIdAndRoleIdByUserIds(userIds);
        boolean flag = userDao.deleteBatchIds(Arrays.asList(userIds)) > 0;
        Integer code;
        String msg;
        code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        msg = flag ? "删除成功" : "删除失败";
        return new Result(code, msg);
    }

    @Override
    public Result delete(long userId) {
        System.out.println(userId);
        userDao.deleteUserIdAndRoleIdByUserId(userId);
        boolean flag = userDao.deleteById(userId) > 0;
        Integer code;
        String msg;
        code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        msg = flag ? "删除成功" : "删除失败";
        return new Result(code, msg);
    }

    @Override
    public User login(User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getUsername, user.getUsername());
        System.out.println(user.getUsername());
        User userInfo = userDao.selectOne(lqw);
        if (userInfo == null)
            return null;
        if (bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword()))
            return userInfo;
        return null;
    }

    @Override
    public Result forgetPwd(User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getUsername, user.getUsername());
        User userInfo = userDao.selectOne(lqw);
        if (userInfo == null) {
            new Result(Code.FORGET_PWD_ERR, "不存在此用户");
        }
        if (!userInfo.getEmail().equals(user.getEmail()))
            return new Result(Code.FORGET_PWD_ERR, "用户邮件错误");
        if (!userInfo.getPhoneNum().equals(user.getPhoneNum()))
            return new Result(Code.FORGET_PWD_ERR, "用户号码错误");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.updateUserPwdByUsername(user);
        return new Result(Code.FORGET_PWD_OK, "修改成功");
    }

    @Override
    public Result editPwd(User user, String pwd) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getUsername, user.getUsername());
        User userInfo = userDao.selectOne(lqw);
        if (!bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword()))
            return new Result(Code.UPDATE_ERR, "原始密码错误");
        user.setPassword(bCryptPasswordEncoder.encode(pwd));
        userDao.updateUserPwdByUsername(user);
        return new Result(Code.UPDATE_OK, "修改密码成功");
    }
}
