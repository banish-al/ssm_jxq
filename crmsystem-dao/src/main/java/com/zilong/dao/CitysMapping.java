package com.zilong.dao;

import com.zilong.vo.Area;
import com.zilong.vo.City;
import com.zilong.vo.Province;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitysMapping {
  List<Province> queryAllProvince();

  List<City> queryCityByPid(@Param("provincecode") String provincecode);

  List<Area> queryAreaByCid(@Param("citycode") String citycode);


  String queryProvinceByCode(String code);

  String queryCityByCode(String code);

  String queryAreaByCode(String code);
}