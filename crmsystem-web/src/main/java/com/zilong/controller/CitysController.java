package com.zilong.controller;

import com.zilong.service.systemService.CitysService;
import com.zilong.vo.Area;
import com.zilong.vo.City;
import com.zilong.vo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CitysController {

    @Autowired
    CitysService citysService;

    @RequestMapping("/queryAllProvince.action")
    public List<Province> queryAllProvince() {
        return citysService.queryAllProvince();
    }

    @RequestMapping("/queryCityByPid.action")
    public List<City> queryCityByPid(String provincecode) {
        return citysService.queryCityByPid(provincecode);
    }

    @RequestMapping("/queryAreaByCid.action")
    public List<Area> queryAreaByCid(String citycode) {
        return citysService.queryAreaByCid(citycode);
    }

}
