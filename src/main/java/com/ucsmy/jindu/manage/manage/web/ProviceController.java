package com.ucsmy.jindu.manage.manage.web;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/16

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Province;
import com.ucsmy.jindu.manage.manage.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 省编号
 *
 * @author ucs_mojiazhou
 * @since 2017/6/16
 */
@Controller
@RequestMapping("province")
public class ProviceController {

    @Autowired
   private ProvinceService provinceService;

    @RequestMapping("query")
    @ResponseBody
    public AosResult getProvinces()
    {
        return provinceService.getAllProvice();
    }

}