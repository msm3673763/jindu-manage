package com.ucsmy.jindu.manage.manage.service.impl;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/16

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.dao.ProvinceMapper;
import com.ucsmy.jindu.manage.manage.entity.Province;
import com.ucsmy.jindu.manage.manage.service.ProvinceService;
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
public class ProviceServiceImpl implements ProvinceService {

    private final static String PROVINCE_REDIS = "jindu:area:province";

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private SysCacheService sysCacheService;

    @Override
    public AosResult getAllProvice() {

        List<Province> list = (List<Province>) sysCacheService.get(PROVINCE_REDIS);
        if(null==list || list.size()==0)
        {
            list = provinceMapper.getProvince();
            sysCacheService.set(PROVINCE_REDIS,list);
        }
        List<Map> maps = new ArrayList<>();
        for (Province province:list)
        {
            Map map = new HashMap();
            map.put("value", province.getProvinceNo());
            map.put("option", province.getProvinceName());
            maps.add(map);
        }


        return AosResult.retSuccessMsg("success",maps);
    }
}