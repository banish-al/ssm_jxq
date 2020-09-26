package com.zilong.service.systemService;

import com.zilong.vo.systemVo.Role;

import java.util.List;

public interface RoleService {

    List<Role> queryAll();

    //添加一个角色
    int addRole(String roleName);
}
