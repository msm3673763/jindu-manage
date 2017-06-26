package com.ucsmy.jindu.manage.manage.web;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/19

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.BankInfo;
import com.ucsmy.jindu.manage.manage.service.BankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/19
 */
@Controller
@RequestMapping("bankInfo")
public class BankInfoController {

    @Autowired
    private BankInfoService bankInfoService;

    @RequestMapping("query")
    @ResponseBody
    public PageResult<BankInfo> query(BankInfo bankInfo,
                                      @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize)
    {
        return bankInfoService.query(bankInfo,pageNum,pageSize);
    }

    @RequestMapping("using")
    @ResponseBody
    public AosResult using(BankInfo bankInfo)
    {
        return bankInfoService.using(bankInfo);
    }

    @RequestMapping("unUsing")
    @ResponseBody
    public AosResult unUsing(BankInfo bankInfo)
    {
        return bankInfoService.unUsing(bankInfo);
    }
}