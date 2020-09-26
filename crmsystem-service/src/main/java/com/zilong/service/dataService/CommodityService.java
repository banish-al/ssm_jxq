package com.zilong.service.dataService;

import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;

public interface CommodityService {
    PageVo<Commodity> queryLikeCommodity(Commodity commodity, int startIndex, int pageSize,String commodityOndateText);

    // 修改商品
    int uptCommodity(Commodity commodity);

    // 下架商品
    int inCommodity(String arr);

    // 上架商品
    int onCommodity(String arr);
    // 根据商品id查询商品总数
    int queryCommodityCountByCid(int cid);

    // 差单个商品数量 cid   wid
    int queryCommodityCountByOneCid(int cid,int wid);
}
