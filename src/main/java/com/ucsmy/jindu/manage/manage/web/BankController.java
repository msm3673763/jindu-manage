package com.ucsmy.jindu.manage.manage.web;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Bank;
import com.ucsmy.jindu.manage.manage.entity.Email;
import com.ucsmy.jindu.manage.manage.ext.BankDetailPojo;
import com.ucsmy.jindu.manage.manage.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_mojiazhou on 2017/6/13.
 */
@Controller
@RequestMapping("bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @RequestMapping("query")
    @ResponseBody
    public PageResult<BankDetailPojo> query(BankDetailPojo bankDetailPojo,
                                            @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize)
    {
        return bankService.query(bankDetailPojo,pageNum,pageSize);
    }

    @RequestMapping("add")
    @ResponseBody
    public AosResult addJinDuEmail(Bank bank)
    {
        return bankService.add(bank);
    }

    @RequestMapping("del")
    @ResponseBody
    public AosResult delJinDuEmail(String id)
    {
        return bankService.delete(id);
    }

    @RequestMapping("update")
    @ResponseBody
    public AosResult update(Bank bank)
    {
        return bankService.update(bank);
    }

    @RequestMapping("queryDetail")
    @ResponseBody
    public BankDetailPojo queryDetail(String id)
    {
        return bankService.queryDetail(id);
    }


}
