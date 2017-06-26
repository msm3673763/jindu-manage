package com.ucsmy.jindu.manage.manage.service.impl;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/16

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.dao.CityMapper;
import com.ucsmy.jindu.manage.manage.entity.City;
import com.ucsmy.jindu.manage.manage.entity.Province;
import com.ucsmy.jindu.manage.manage.service.CityService;
import com.ucsmy.jindu.manage.manage.service.SysCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/16
 */

@Service
public class CityServiceImpl implements CityService {

    private final static String CITY_REDIS = "jindu:area:city:";

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private SysCacheService sysCacheService;

    @Override
    public AosResult getCitysByProvince(String provinceNo) {


        List<City> list = (List<City>) sysCacheService.get(CITY_REDIS+provinceNo);
        if(null==list || list.size()==0)
        {
            list  = cityMapper.getCitys(provinceNo);
            sysCacheService.set(CITY_REDIS+provinceNo,list);
        }
        List<Map> maps = new ArrayList<>();
        for (City city:list)
        {
            Map map = new HashMap();
            map.put("value", city.getCityNo());
            map.put("option", city.getCityName());
            maps.add(map);
        }
        return AosResult.retFailureMsg("success",maps);
    }
}