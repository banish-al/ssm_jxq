package com.zilong.service.systemService.impl;

import com.zilong.dao.systemDao.RoleMapping;
import com.zilong.service.systemService.RoleService;
import com.zilong.vo.systemVo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapping roleMapping;


    @Override
    public List<Role> queryAll() {
        return roleMapping.queryAll();
    }

    @Override
    public int addRole(String roleName) {
        return roleMapping.addRole(roleName);
    }
}
