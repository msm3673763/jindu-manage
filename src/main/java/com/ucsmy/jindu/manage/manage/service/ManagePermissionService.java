package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.config.shiro.ShiroRealmImpl;
import com.ucsmy.jindu.manage.manage.entity.ManagePermission;

import java.util.List;

/**
 * Created by 
 * @author  chenqilin on 2017/4/14.
 */
public interface ManagePermissionService {

    /**
     * 根据ModuleID查询
     * @param moduleId
     * @return
     */
    List<ManagePermission> queryPermissionByModuleID (String moduleId);

    int addPermission(ManagePermission permission);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    ManagePermission getPermissionById(String id);

    int updatePermission(ManagePermission permission);

    int deletePermissionByID(String id);

    /**
     * 验证permission属性值
     * @param permission
     * @return
     */
    AosResult validatePermission(ManagePermission permission);

    /**
     * 根据登录的user查询权限，如果是admin登录查询所有权限
     * @param user
     * @return
     */
    List<ManagePermission> queryPermissionByUser(ShiroRealmImpl.LoginUser user);

}
