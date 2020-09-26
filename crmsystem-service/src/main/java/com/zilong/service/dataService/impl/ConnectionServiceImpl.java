package com.zilong.service.dataService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.ConnectionMapping;
import com.zilong.service.dataService.ConnectionService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    ConnectionMapping connectionMapping;

    @Override
    public List<Connection> queryAll() {
        return null;
    }

    @Override
    public Connection queryById(int id) {
        return null;
    }

    @Override
    public PageVo<Connection> queryLike(Connection connection, int startIndex, int pageSize) {
        PageVo<Connection> pageVo = new PageVo<>();

        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(connectionMapping.queryLike(connection));

        pageVo.setTotal(connectionMapping.queryLikeCount(connection));
        return pageVo;
    }

    @Override
    public Connection queryByName(String name) {
        return connectionMapping.queryByName(name);
    }

    @Override
    public int insert(Connection connection) {
        return connectionMapping.insert(connection);
    }

    @Override
    public int updateById(Connection connection) {
        return connectionMapping.updateById(connection);
    }
}
