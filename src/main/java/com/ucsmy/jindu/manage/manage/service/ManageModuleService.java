package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.jindu.manage.config.shiro.ShiroRealmImpl;
import com.ucsmy.jindu.manage.manage.entity.ManageModule;
import com.ucsmy.jindu.manage.manage.ext.MainModulePojo;
import com.ucsmy.jindu.manage.manage.ext.ModuleTreePojo;

import java.util.List;

/**
 * Created by 
 * @author  chenqilin on 2017/4/13.
 */
public interface ManageModuleService {

    /**
     * 根据条件查询
     * @param name 菜单名称
     * @param parentId 父节点ID
     * @param excludeId 排除的节点
     * @return
     */
    List<ModuleTreePojo> getModuleListByCondition(String name, String parentId, String excludeId);

    /**
     * 根据名称查询
     * @param name 非必填
     * @return
     */
    List<ModuleTreePojo> getModuleListByCondition(String name);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    ModuleTreePojo getModuleDetail(String id);

    int addModule(ManageModule module);

    int updateModule(ManageModule module);

    int getChildrenNum(String id);

    int deleteModule(String id);

    List<MainModulePojo> queryMainModuleByUser(ShiroRealmImpl.LoginUser user);
}
