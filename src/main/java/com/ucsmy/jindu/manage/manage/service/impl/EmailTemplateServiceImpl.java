package com.ucsmy.jindu.manage.manage.service.impl;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.HibernateValidateUtils;
import com.ucsmy.jindu.manage.commons.base.utils.StringAndNumberUtil;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDUtils;
import com.ucsmy.jindu.manage.manage.dao.EmailTemplateMapper;
import com.ucsmy.jindu.manage.manage.dao.ParameterMapper;
import com.ucsmy.jindu.manage.manage.entity.EmailTemplate;
import com.ucsmy.jindu.manage.manage.entity.Parameter;
import com.ucsmy.jindu.manage.manage.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by 
 * @author ucs_mojiazhou on 2017/5/11.
 */
@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {


    @Autowired
    private EmailTemplateMapper emailTemplateMapper;

    @Autowired
    private ParameterMapper parameterMapper;

    public static final int PAGENUM = 1;
    public static final int PAGESIZE = 10;
    public static final String TEMPLATE_TYPE = "template_type";

    /**
     * 分页查询模板
     * @param title 模板id
     * @param pageNum 请求页码
     * @param pageSize 请求条数
     * @return
     */
    @Override
    public AosResult queryTemplateList(String title, int pageNum, int pageSize){
        if(pageNum <= 0){
            pageNum = PAGENUM;
        }
        if(pageSize <= 0){
            pageSize = PAGESIZE;
        }
        PageResult<EmailTemplate> emailTemplateList = this.emailTemplateMapper.queryTemplateListByPage(title, new PageRequest(pageNum, pageSize));
        return AosResult.retSuccessMsg("", emailTemplateList);
    }

    /**
     * 添加模板
     * @param template 模板信息
     */
    @Override
    public AosResult addTemplate(EmailTemplate template){

        String errorMsg = HibernateValidateUtils.getErrors(template,
                            new String[]{"模板主题不能为空"});
        if (!StringAndNumberUtil.isNull(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        }
        //获取模板类型参数
        Parameter type = this.parameterMapper.queryParameterByTypeAndKey(TEMPLATE_TYPE, template.getType());
        if(type == null){
            return AosResult.retFailureMsg("所选模板类型不存在，操作失败");
        }
        template.setType(type.getId());
        //查询该类型是否已存在模板
        long count = this.emailTemplateMapper.countTemplateByType(template.getType());
        if(count > 0){
            return AosResult.retFailureMsg("已存在该类型的模板，操作失败");
        }
        String id = UUIDUtils.getUUID32();
        template.setId(id);
        template.setIsDeleted(EmailTemplate.DELETED_NO);
        int result = this.emailTemplateMapper.addTemplate(template);
        if(result == 0){
            return AosResult.retFailureMsg("添加失败");
        }
        return AosResult.retSuccessMsg("添加成功");
    }

    /**
     * 修改模板
     * @param template 模板信息
     */
    @Override
    public AosResult updateTemplate(EmailTemplate template){
        if(null == template || StringAndNumberUtil.isNullAfterTrim(template.getId())){
            return AosResult.retFailureMsg("参数错误");
        }
        String errorMsg = HibernateValidateUtils.getErrors(template,
                new String[]{"模板主题不能为空"});
        if (!StringAndNumberUtil.isNull(errorMsg)) {
            return AosResult.retFailureMsg(errorMsg);
        }
        //获取模板类型参数
        Parameter type = this.parameterMapper.queryParameterByTypeAndKey(TEMPLATE_TYPE, template.getType());
        if(type == null){
            return AosResult.retFailureMsg("所选模板类型不存在，操作失败");
        }
        template.setType(type.getId());
        //判断类型是否已存在模板
        List<EmailTemplate> emailTemplates = this.emailTemplateMapper.queryTemplateListByType(template.getType());
        if(emailTemplates != null && emailTemplates.size() > 0){
            EmailTemplate temp = emailTemplates.get(0);
            if(!template.getId().equals(temp.getId())){                //排除本身
                return AosResult.retFailureMsg("已存在该类型的模板，操作失败");
            }
        }
        int result = this.emailTemplateMapper.updateTemplate(template);
        if(result == 0){
            return AosResult.retFailureMsg("修改失败");
        }
        return AosResult.retSuccessMsg("修改成功");
    }


    /**
     * 逻辑删除模板
     * @param templateId 模板编号
     */
    @Override
    public AosResult deleteTemplate(String templateId){
        if(StringAndNumberUtil.isNullAfterTrim(templateId)){
            return AosResult.retFailureMsg("参数错误");
        }
        int result = this.emailTemplateMapper.updateTemplateToDelete(templateId);
        if(result == 0){
            return AosResult.retFailureMsg("删除失败");
        }
        return AosResult.retSuccessMsg("删除成功");
    }

    /**
     * 获取指定类型的模板
     * @param type 模板类型
     */
    @Override
    public AosResult queryTemplateByType(String type){
        if(StringAndNumberUtil.isNullAfterTrim(type)){
            return AosResult.retFailureMsg("参数错误");
        }
        EmailTemplate template = this.emailTemplateMapper.queryTemplateByTypeValue(type);
        if(template == null){
            return AosResult.retFailureMsg("模板不存在");
        }
        return AosResult.retSuccessMsg("", template);
    }
}
