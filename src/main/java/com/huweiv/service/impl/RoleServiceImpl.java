package com.huweiv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huweiv.domain.PageBean;
import com.huweiv.domain.Result;
import com.huweiv.domain.Role;
import com.huweiv.dao.RoleDao;
import com.huweiv.domain.User;
import com.huweiv.service.RoleService;
import com.huweiv.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @CreateTime 2022/4/10 10:28
 */
@Service("roleService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Result add(Role role) {
        Role roleInfo = roleDao.selectOneByRoleName(role.getRoleName());
        if (null != roleInfo && roleInfo.getDeleted() == 0)
            return new Result(Code.SAVE_ERR, "角色名已被注册");
        boolean flag;
        if (null == roleInfo)
            flag = roleDao.insert(role) > 0;
        else {
            role.setId(roleInfo.getId());
            role.setDeleted(0);
            flag = roleDao.update(role) > 0;
        }
        Integer code = flag ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag ? "添加成功" : "添加失败";
        return new Result(code, msg);
    }

    @Override
    public Result edit(Role role, int isUpdateRoleName) {
        if (isUpdateRoleName == 0)
            role.setRoleName(null);
        else {
            Role roleInfo = roleDao.selectOneByRoleName(role.getRoleName());
            if (null != roleInfo)
                return new Result(Code.UPDATE_ERR, "角色名已被注册");
        }
        boolean flag = roleDao.updateById(role) > 0;
        Integer code = flag ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag ? "编辑成功" : "编辑失败";
        return new Result(code, msg);
    }

    @Override
    public Result delete(Long roleId) {
        roleDao.deleteUserIdAndRoleIdByRoleId(roleId);
        boolean flag = roleDao.deleteById(roleId) > 0;
        Integer code;
        String msg;
        code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        msg = flag ? "删除成功" : "删除失败";
        return new Result(code, msg);
    }

    @Override
    public Result batchDelete(Long[] roleIds) {
        roleDao.deleteUserIdAndRoleIdByRoleIds(roleIds);
        boolean flag = roleDao.deleteBatchIds(Arrays.asList(roleIds)) > 0;
        Integer code;
        String msg;
        code = flag ? Code.DELETE_OK : Code.DELETE_ERR;
        msg = flag ? "删除成功" : "删除失败";
        return new Result(code, msg);
    }

    @Override
    public PageBean<Role> list(Role role, int currentPage, int pageSize) {
        IPage page = new Page(currentPage, pageSize);
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<Role>();
        lqw.like(null != role.getRoleName() && !"".equals(role.getRoleName()), Role::getRoleName, role.getRoleName())
                .like(null != role.getRoleDesc() && !"".equals(role.getRoleDesc()), Role::getRoleDesc, role.getRoleDesc());
        roleDao.selectPage(page, lqw);
        List<Role> roleList  = page.getRecords();
        int totalCount = (int) page.getTotal();
        PageBean<Role> pageBean = new PageBean<>();
        pageBean.setRows(roleList);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public List<Role> selectAll() {
        return roleDao.selectList(null);
    }
}
