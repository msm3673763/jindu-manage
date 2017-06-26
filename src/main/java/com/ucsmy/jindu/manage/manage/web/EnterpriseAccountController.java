package com.ucsmy.jindu.manage.manage.web;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.ext.AuthDetailPojo;
import com.ucsmy.jindu.manage.manage.ext.DataPageResult;
import com.ucsmy.jindu.manage.manage.service.EnterpriseAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_zhengfucheng on 2017/6/20.
 */
@Controller
@RequestMapping("enterpriseAccount")
public class EnterpriseAccountController {


    @Autowired
    private EnterpriseAccountService enterpriseAccountService;


    @RequestMapping("query")
    @ResponseBody
    public DataPageResult query(@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize,
                                String status, String startDate, String endDate, String depositor) {
        return enterpriseAccountService.listEnterpriseAccount(pageNum, pageSize, status, startDate, endDate, depositor);
    }


    @RequestMapping("countCompany")
    @ResponseBody
    public AosResult countCompany()
    {
        return enterpriseAccountService.countCompany();
    }

    @RequestMapping("updateCorporDepositNoStatus")
    @ResponseBody
    public AosResult updateCorporDepositNoStatus(@RequestParam(required = true)String recordId)
    {
        return enterpriseAccountService.updateCorporDepositNoStatus(recordId);
    }


}
