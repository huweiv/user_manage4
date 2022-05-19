package com.huweiv.service;

import com.huweiv.domain.PageBean;
import com.huweiv.domain.Result;
import com.huweiv.domain.Role;

import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName RoleService
 * @Description TODO
 * @CreateTime 2022/4/10 10:25
 */
public interface RoleService {

    Result add(Role role);
    Result edit(Role role, int isUpdateRoleName);
    Result delete(Long roleId);
    Result batchDelete(Long[] roleIds);
    PageBean<Role> list(Role role, int currentPage, int pageSize);
    List<Role> selectAll();

}
