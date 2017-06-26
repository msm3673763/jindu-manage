package com.ucsmy.jindu.manage.manage.service;


import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.EmailTemplate;

/**
 * Created by 
 * @author ucs_xujunwei on 2017/6/13.
 */
public interface EmailTemplateService {

    /**
     * 分页查询模板
     * @param title 模板id
     * @param pageNum 请求页码
     * @param pageSize 请求条数
     * @return
     */
    AosResult queryTemplateList(String title, int pageNum, int pageSize);

    /**
     * 添加模板
     * @param template 模板信息
     */
    AosResult addTemplate(EmailTemplate template);

    /**
     * 修改模板
     * @param template 模板信息
     */
    AosResult updateTemplate(EmailTemplate template);

    /**
     * 逻辑删除模板
     * @param templateId 模板编号
     */
    AosResult deleteTemplate(String templateId);

    /**
     * 获取指定类型的模板
     * @param type 模板类型
     */
    AosResult queryTemplateByType(String type);

}
