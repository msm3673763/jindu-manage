package com.ucsmy.jindu.manage.manage.service.impl;

//import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.StringUtils;
import com.ucsmy.jindu.manage.config.shiro.ShiroRealmImpl;
import com.ucsmy.jindu.manage.manage.dao.ManagePermissionMapper;
import com.ucsmy.jindu.manage.manage.entity.ManagePermission;
import com.ucsmy.jindu.manage.manage.service.ManagePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 
 * @author  chenqilin on 2017/4/14.
 */
@Service
public class ManagePermissionServiceImpl implements ManagePermissionService {

    @Autowired
    private ManagePermissionMapper managePermissionMapper;

    @Override
    @Logger(printSQL = true)
    public List<ManagePermission> queryPermissionByModuleID(String moduleId) {
        return managePermissionMapper.queryPermissionByModuleID(moduleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addPermission(ManagePermission permission) {
        return managePermissionMapper.addPermission(permission);
    }

    @Override
    public ManagePermission getPermissionById(String id) {
        return managePermissionMapper.getPermissionById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePermission(ManagePermission permission) {
        return managePermissionMapper.updatePermission(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePermissionByID(String id) {
        return managePermissionMapper.deletePermissionByID(id);
    }

    @Override
    public AosResult validatePermission(ManagePermission permission) {
        if (StringUtils.isEmpty(permission.getModuleId())) {
            return AosResult.retFailureMsg("菜单ID不能为空");
        }
        if (StringUtils.isEmpty(permission.getName())) {
            return AosResult.retFailureMsg("权限名称不能为空");
        }
        if (StringUtils.isEmpty(permission.getUrlAction())) {
            return AosResult.retFailureMsg("资源URL不能为空");
        }
        if (permission.getName().length() > 64) {
            return AosResult.retFailureMsg("权限名称长度不能大于64");
        }
        if (permission.getDescription().length() > 256) {
            return AosResult.retFailureMsg("描述长度不能大于256");
        }
        if (permission.getUrlAction().length() > 256) {
            return AosResult.retFailureMsg("资源URL长度不能大于256");
        }
        if (permission.getSn().length() > 36) {
            return AosResult.retFailureMsg("判断标识长度不能大于36");
        }
        // 权限名称不能相同
        List<ManagePermission> list = managePermissionMapper.queryPermissionByCondition(permission.getModuleId()
                , permission.getName(), permission.getPermissionId());
        if (!list.isEmpty()) {
            return AosResult.retFailureMsg("该菜单下存在同名权限");
        }
        // 判断标识不能相同
        Map<String, String> map = new HashMap<>();
        map.put("moduleId", permission.getModuleId());
        map.put("permissionId", permission.getPermissionId());
        map.put("sn", permission.getSn());
        ManagePermission bean = managePermissionMapper.queryPermissionByMap(map);
        if (bean != null) {
            return AosResult.retFailureMsg("该菜单下存在相同判断标识的权限");
        }
        return AosResult.retSuccessMsg("success");
    }

    @Override
    @Logger(printSQL = true)
    public List<ManagePermission> queryPermissionByUser(ShiroRealmImpl.LoginUser user) {
        if ("admin".equals(user.getUserName())) {
            return managePermissionMapper.queryPermissionAll();
        } else {
            return managePermissionMapper.queryPermissionByUserID(user.getId());
        }
    }
}
