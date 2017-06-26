package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.EmailTemplate;
import com.ucsmy.jindu.manage.manage.entity.ManageConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 
 * @author  ucs_xujunwei on 2017/6/13.
 */
@Mapper
public interface EmailTemplateMapper {

    /**
     * 根据编号查询指定模板
     * @param title 模板编号
     * @param pageRequest 分页实体
     * @return
     */
    PageResult<EmailTemplate> queryTemplateListByPage(@Param("title")String title, PageRequest pageRequest);

    /**
     * 添加模板
     * @param template 模板信息
     * @return
     */
    int addTemplate(EmailTemplate template);

    /**
     * 修改模板
     * @param template 要修改的模板内容
     * @return
     */
    int updateTemplate(EmailTemplate template);

    /**
     * 修改模板为删除状态
     * @param id 指定编号
     * @return
     */
    int updateTemplateToDelete(String id);

    /**
     * 获取指定类型的模板数目
     * @param type
     * @return
     */
    long countTemplateByType(String type);

    /**
     * 获取指定类型的模板
     * @param type
     * @return
     */
    List<EmailTemplate> queryTemplateListByType(String type);


    /**
     * 获取指定类型的唯一模板
     * @param type
     * @return
     */
    EmailTemplate queryTemplateByTypeValue(String type);

}
