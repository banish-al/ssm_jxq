package com.zilong.service.systemService;

import com.zilong.vo.Area;
import com.zilong.vo.City;
import com.zilong.vo.Province;

import java.util.List;

public interface CitysService {
    List<Province> queryAllProvince();

    List<City> queryCityByPid(String code);

    List<Area> queryAreaByCid(String code);

    String queryProvinceByCode(String code);

    String queryCityByCode(String code);

    String queryAreaByCode(String code);
}
