package com.zilong.service.systemService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.systemDao.UsersMapping;
import com.zilong.service.systemService.UsersService;
import com.zilong.vo.systemVo.Menu;
import com.zilong.vo.PageVo;
import com.zilong.vo.systemVo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersMapping usersMapping;


    @Override
    public Users queryById(int id) {
        return usersMapping.queryById(id);
    }


    @Override
    public Users queryByVo(Users users) {
        return usersMapping.queryByVo(users);
    }

    @Override
    public List<Menu> queryMenuByUid(int uid, int pid) {
        return usersMapping.queryMenuByUid(uid, pid);
    }

    @Override
    public List<Menu> queryMenuAll(int pid) {
        return usersMapping.queryMenuAll(pid);
    }

    @Override
    public List<Menu> queryMenuFristByUid(int uid) {
        return usersMapping.queryMenuFristByUid(uid);
    }

    @Override
    public PageVo<Users> queryLike(Users users, int startIndex, int pageSize, int roleId) {
        PageVo<Users> pageVo = new PageVo<>();
        if ("全部".equals(users.getUserState())) {
            users.setUserState(null);
        }

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(usersMapping.queryLike(users, roleId));

        pageVo.setTotal(usersMapping.queryLikeCount(users, roleId));
        return pageVo;
    }

    @Override
    public String userNumber() {
        return usersMapping.userNumber();
    }

    public int insert(Users users) {
        Integer number = Integer.valueOf(usersMapping.userNumber());
        users.setUserNumber(number + 1 + "");
        users.setUserState("在职");

        return usersMapping.insert(users);
    }

    public int addRoleAsUser(int userId, int roleId) {
        return usersMapping.addRoleAsUser(userId, roleId);
    }

    @Override
    public int uptUserById(Users users) {
        return usersMapping.updateById(users);
    }

    // 给用户添加角色
    public int addUser_role(int userId, int roleId) {
        return usersMapping.addUser_role(userId, roleId);
    }

    // 根据员工id删除他的角色
    public int delUserRoleByUserId(int userId) {
        return usersMapping.delUserRoleByUserId(userId);
    }
}
