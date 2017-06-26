package com.ucsmy.jindu.manage.manage.web;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/16

 * Contributors:
 *      - initial implementation
 */
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.City;
import com.ucsmy.jindu.manage.manage.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/16
 */
@Controller
@RequestMapping("city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping("query")
    @ResponseBody
    public AosResult getCitys(String provinceNo)
    {
        return cityService.getCitysByProvince(provinceNo);
    }

}