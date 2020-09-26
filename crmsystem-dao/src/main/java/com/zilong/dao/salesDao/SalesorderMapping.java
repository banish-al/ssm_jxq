package com.zilong.dao.salesDao;

import com.zilong.vo.salesVo.Salesorder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//** custom import **//
//在这里写你的自定义代码导入的包,每次生成.java文件都会保留这一段代码

//** /custom import **//

@Repository
public interface SalesorderMapping {

//** custom methods **//
    //在这里写你的自定义接口,每次生成.java文件都会保留这一段代码


//** /custom methods **//

    /**
     * 查询所有方法
     */
    List<Salesorder> queryAll();


    /**
     * 根据Salesorder条件模糊查询多条数据方法
     */
    List<Salesorder> queryLike(Salesorder salesorder);

    /**
     * 根据Salesorder模糊查询数据总条数方法
     */
    int queryLikeCount(Salesorder salesorder);


    /**
     * 根据Salesorder条件修改单条数据方法,从传入对象获取id
     */
    int updateById(Salesorder salesorder);

    //添加销售订单
    int addSalesorder(Salesorder salesorder);

    // 添加销售详情订单
    int addSalesorderDetails(@Param("sid") int sid, @Param("cid") int cid, @Param("count") int count);

    //根据商品id和采购订单id查询商品数量
    int queryCountByCidOrSid2(@Param("cid") int cid,@Param("sid") int sid);

    // 根据id查询销售订单
    Salesorder querySalesorderById(int salesorder_id);

    // 根据id修改销售订单状态
    int uptSalesorderStateById(int salesorderId);

    // 拒接采购订单
    int noSalesorderStateById(int salesorderId);

    // 根据sid查connectionId
    int queryConnectionIdBySid(int sid);
}