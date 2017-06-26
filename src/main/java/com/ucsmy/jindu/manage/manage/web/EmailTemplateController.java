package com.ucsmy.jindu.manage.manage.web;

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.EmailTemplate;
import com.ucsmy.jindu.manage.manage.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_xujunwei on 2017/6/13.
 */
@Controller
@RequestMapping("/template")
public class EmailTemplateController {

    @Autowired
    private EmailTemplateService emailTemplateService;

    @RequestMapping("/queryTemplateList")
    @ResponseBody
    public AosResult queryTemplateList(String title, int pageNum, int pageSize){
        return this.emailTemplateService.queryTemplateList(title, pageNum, pageSize);
    }

    @RequestMapping("/addTemplate")
    @ResponseBody
    public AosResult addTemplate(EmailTemplate template){
        return this.emailTemplateService.addTemplate(template);
    }

    @RequestMapping("/updateTemplate")
    @ResponseBody
    public AosResult updateTemplate(EmailTemplate template){
        return this.emailTemplateService.updateTemplate(template);
    }

    @RequestMapping("/deleteTemplate")
    @ResponseBody
    public AosResult deleteTemplate(String templateId){
        return this.emailTemplateService.deleteTemplate(templateId);
    }

    @RequestMapping("/queryTemplate")
    @ResponseBody
    public AosResult queryTemplate(String type){
        return this.emailTemplateService.queryTemplateByType(type);
    }

}
