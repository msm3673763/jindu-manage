package com.ucsmy.jindu.manage.manage.web;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Email;
import com.ucsmy.jindu.manage.manage.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_mojiazhou on 2017/6/13.
 */
@Controller
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("query")
    @ResponseBody
    public PageResult<Email> query(@RequestParam(required = false) String name,
                                   @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize)
    {
        return emailService.query(name,pageNum,pageSize);
    }

    @RequestMapping("add")
    @ResponseBody
    public AosResult addJinDuEmail(Email email)
    {
        return emailService.addJinDuEmail(email);
    }

    @RequestMapping("del")
    @ResponseBody
    public AosResult delJinDuEmail(String uuid)
    {
        return emailService.delJinDuEmail(uuid);
    }

    @RequestMapping("update")
    @ResponseBody
    public AosResult update(Email email)
    {
        return emailService.updateJinDuEmail(email);
    }

    @RequestMapping("queryDetail")
    @ResponseBody
    public Email queryDetail(String uuid)
    {
        return emailService.queryDetail(uuid);
    }

}
