package com.zilong.service.dataService;

import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Warehouse;

import java.util.List;

public interface WarehouseService {
    // 模糊查询仓库信息
    PageVo<Warehouse> queryLikeWarehouse(Warehouse warehouse, int startIndex, int pageSize);

    // 修改
    int uptWarehouse(Warehouse warehouse);

    // 根据仓库id查询剩余容量
    int queryWarehouseCountByWid(int wid);

    List<Warehouse> queryAllWarehouse();


}
