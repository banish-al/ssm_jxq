package com.zilong.service.dataService;

import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Connection;

import java.util.List;

public interface ConnectionService {
    /**
     * 查询所有方法
     */
    List<Connection> queryAll();

    /**
     * 根据主键connection_id(Connection.connectionId)查询单条数据方法
     */
    Connection queryById(int id);

    /**
     * 根据Connection条件模糊查询多条数据方法
     */
    PageVo<Connection> queryLike(Connection connection, int startIndex, int pageSize);
    // 根据名字查询单个
    Connection queryByName(String name);

    /**
     * 根据Connection插入数据方法
     */
    int insert(Connection connection);

    /**
     * 根据Connection条件修改单条数据方法,从传入对象获取id
     */
    int updateById(Connection connection);
}
