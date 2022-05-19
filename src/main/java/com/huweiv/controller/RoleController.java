package com.huweiv.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huweiv.domain.PageBean;
import com.huweiv.domain.Result;
import com.huweiv.domain.Role;
import com.huweiv.service.RoleService;
import com.huweiv.util.Code;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName RoleController
 * @Description TODO
 * @CreateTime 2022/4/10 10:29
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource(name = "roleService")
    private RoleService roleService;
    @Resource(name = "jackJson")
    ObjectMapper objectMapper;

    @PostMapping("/{currentPage}/{pageSize}")
    public Result list(@RequestBody Role role, @PathVariable int currentPage, @PathVariable int pageSize) {
        PageBean<Role> rolePageBean =  roleService.list(role, currentPage, pageSize);
        return new Result(rolePageBean, Code.GET_PAGE_OK);
    }

    @PostMapping
    public Result add(@RequestBody Role role) {
        return roleService.add(role);
    }

    @PutMapping
    public Result edit(@RequestBody String params) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(params);
        Role role = objectMapper.readValue(jsonNode.get("role").toString(), Role.class);
        int isUpdateRoleName = jsonNode.get("isUpdateRoleName").asInt();
        return roleService.edit(role, isUpdateRoleName);
    }

    @DeleteMapping("/{roleId}")
    public Result delete(@PathVariable Long roleId) {
         return roleService.delete(roleId);
    }

    @DeleteMapping
    public Result batchDelete(@RequestBody Long[] selectIds) {
        return roleService.batchDelete(selectIds);
    }

    @GetMapping
    public Result selectAll() {
        List<Role> roleList = roleService.selectAll();
        return new Result(roleList, Code.GET_OK);
    }
}
