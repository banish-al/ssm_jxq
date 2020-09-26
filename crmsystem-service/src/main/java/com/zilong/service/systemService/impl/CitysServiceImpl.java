package com.zilong.service.systemService.impl;

import com.zilong.dao.CitysMapping;
import com.zilong.service.systemService.CitysService;
import com.zilong.vo.Area;
import com.zilong.vo.City;
import com.zilong.vo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitysServiceImpl implements CitysService {

    @Autowired
    CitysMapping citysMapping;

    @Override
    public List<Province> queryAllProvince() {
        return citysMapping.queryAllProvince();
    }

    @Override
    public List<City> queryCityByPid(String provincecode) {
        return citysMapping.queryCityByPid(provincecode);
    }

    @Override
    public List<Area> queryAreaByCid(String citycode) {
        return citysMapping.queryAreaByCid(citycode);
    }

    @Override
    public String queryProvinceByCode(String code) {
        return citysMapping.queryProvinceByCode(code);
    }

    @Override
    public String queryCityByCode(String code) {
        return citysMapping.queryCityByCode(code);
    }

    @Override
    public String queryAreaByCode(String code) {
        return citysMapping.queryAreaByCode(code);
    }
}
