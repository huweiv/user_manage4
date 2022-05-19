package com.huweiv.service;

import com.huweiv.domain.PageBean;
import com.huweiv.domain.Result;
import com.huweiv.domain.User;

public interface UserService {

    PageBean<User> list(User user, Long[] roleIds, int currentPage, int pageSize);
    Result add(User user, Long[] roleIds);
    Result edit(User user, Long[] roleIds, int isUpdateUserName, int isUpdateUserPwd);
    Result batchDelete(Long[] roleIds);
    Result delete(long userId);
    User login(User user);
    Result forgetPwd(User user);
    Result editPwd(User user, String pwd);

}
