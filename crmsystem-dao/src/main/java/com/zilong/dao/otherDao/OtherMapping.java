package com.zilong.dao.otherDao;

import com.zilong.vo.dataVo.Commoditytype;
import com.zilong.vo.dataVo.Supplier;
import com.zilong.vo.purchase.SupplierCommodity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherMapping {
    // 供货商根据邮箱和密码登录
    Supplier loginSupplier(@Param("mailbox") String mailbox, @Param("password")String password);

    // 查询此供货商的商品
    List<SupplierCommodity> querySupplierCommodityBySid(@Param("supplierCommodity") SupplierCommodity supplierCommodity,
                                                        @Param("sid") int sid,@Param("tid") int tid);

    // 查询此供货商的商品个数
    int querySupplierCommodityBySidCount(int sid);

    // 修改供货商商品
    int uptSupplierCommodity(SupplierCommodity supplierCommodity);

    // 添加商品
    int addSupplierCommodity(SupplierCommodity supplierCommodity);
}
