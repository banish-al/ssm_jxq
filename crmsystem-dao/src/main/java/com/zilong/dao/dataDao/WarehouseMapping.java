package com.zilong.dao.dataDao;

import com.zilong.vo.dataVo.Commodity;
import com.zilong.vo.dataVo.Warehouse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//** custom import **//
//在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface WarehouseMapping {

//** custom methods **//
    //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

    /**
     * 查询所有方法
     */
    List<Warehouse> queryAll();

    /**
     * 根据主键warehouse_id(Warehouse.warehouseId)查询单条数据方法
     */
    Warehouse queryById(int id);

    /**
     * 根据Warehouse条件查询多条数据方法
     */
    List<Warehouse> query(Warehouse warehouse);

    /**
     * 根据Warehouse条件模糊查询多条数据方法
     */
    List<Warehouse> queryLike(Warehouse warehouse);

    /**
     * 根据Warehouse条件查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数
     */
    List<Warehouse> queryLimit(@Param("Warehouse") Warehouse warehouse, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 根据Warehouse条件模糊查询数据方法,限制显示条数,startIndex为开始索引,pageSize为显示条数
     */
    List<Warehouse> queryLikeLimit(@Param("Warehouse") Warehouse warehouse, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 根据Warehouse条件获取数据总条数方法
     */
    int queryCount(Warehouse warehouse);

    /**
     * 根据Warehouse模糊查询数据总条数方法
     */
    int queryLikeCount(Warehouse warehouse);

    /**
     * 根据Warehouse插入数据方法
     */
    int insert(Warehouse warehouse);

    /**
     * 根据Warehouse条件修改单条数据方法,从传入对象获取id
     */
    int updateById(Warehouse warehouse);

    /**
     * 根据SetValue条件修改多条数据方法,从传入对象Condition中获取修改的条件<br/>注意:如果Condition为空或Condition所有关键字段为空,则会修改该表所有数据
     */
    int updates(@Param("SetValue") Warehouse setValue, @Param("Condition") Warehouse condition);

    /**
     * 根据Warehouse条件删除单条数据方法,从传入对象获取id
     */
    int deleteById(int id);

    /**
     * 根据Warehouse条件修改多条数据方法,从传入对象获取删除条件<br/>注意:如果参数对象所有关键字段都为空值,则不会删除任何数据
     */
    int deletes(Warehouse warehouse);

    // 根据商品id查询仓库集合
    List<Warehouse> queryWarehouseByCid(int cid);

    // 根据仓库id查询已用容量
    int queryWarehouseCountByWid(int wid);

    // 查询第一个仓库名id
    int queryWarehouseByOne();

    // 查询一个仓库的剩余容量
    int queryWarehouseMaxCount(int wid);

    /**
     * 仓库转库     cid:商品id   count:需要转库的数量  turnWarehouseId:被转仓库id  warehouseId:转到的仓库id
     */
    //根据商品id和仓库id减少商品库存
    int lessenCidByTurnWarehouseId(@Param("cid") int cid, @Param("count") int count, @Param("turnWarehouseId") int turnWarehouseId);

    //判断这个仓库是否已存在这个商品
    int existsByCidOrWarehouseId(@Param("cid") int cid, @Param("warehouseId") int warehouseId);

    //如果不存在就添加商品
    int addCidByWarehouseId(@Param("cid") int cid, @Param("count") int count, @Param("warehouseId") int warehouseId);

    //如果存在就设置商品的数量
    int setCidByWarehouseId(@Param("cid") int cid, @Param("count") int count, @Param("warehouseId") int warehouseId);

    //删除仓库里商品库存为0的商品
    int delCidByCountIsZero();


    //出库
    //根据仓库id和商品id获取商品数量
    int queryCountByCidOrWid(@Param("cid")int cid,@Param("wid")int wid);

    //根据仓库id和商品id清除商品库存
    int delWarehouseCommodityByCidOrWid(@Param("cid")int cid,@Param("wid")int wid);

}