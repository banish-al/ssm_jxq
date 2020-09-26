package com.zilong.service.dataService.impl;

import com.github.pagehelper.PageHelper;
import com.zilong.dao.dataDao.CommodityMapping;
import com.zilong.service.dataService.CommodityService;
import com.zilong.vo.PageVo;
import com.zilong.vo.dataVo.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    CommodityMapping commodityMapping;

    @Override
    public PageVo<Commodity> queryLikeCommodity(Commodity commodity, int startIndex, int pageSize, String commodityOndateText) {
        PageVo<Commodity> pageVo = new PageVo<>();

        if(commodity.getCommodityState() == null){
            commodity.setCommodityState("上架");
        }
        PageHelper.startPage(startIndex, pageSize);
        pageVo.setRows(commodityMapping.queryLike(commodity, commodityOndateText));

        pageVo.setTotal(commodityMapping.queryLikeCount(commodity, commodityOndateText));
        return pageVo;
    }

    @Override
    public int uptCommodity(Commodity commodity) {
        return commodityMapping.updateById(commodity);
    }

    @Override
    public int inCommodity(String arr) {
        String[] row = arr.split(",");
        int i = 1;
        for (String r: row){
            i *= commodityMapping.inCommodity(Integer.valueOf(r));
        }
        return i == 1 ? 1 : 0;
    }

    @Override
    public int onCommodity(String arr) {
        String[] row = arr.split(",");
        int i = 1;
        for (String r: row){
            i *= commodityMapping.onCommodity(Integer.valueOf(r));
        }
        return i == 1 ? 1 : 0;
    }

    @Override
    public int queryCommodityCountByCid(int cid) {
        return commodityMapping.queryCommodityCountByCid(cid);
    }

    @Override
    public int queryCommodityCountByOneCid(int cid, int wid) {
        return commodityMapping.queryCommodityCountByOneCid(cid,wid);
    }
}
