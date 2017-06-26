package com.ucsmy.jindu.manage.manage.web;

//import com.ucsmy.commons.utils.StringUtils;
//import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.ResultConst;
import com.ucsmy.jindu.manage.commons.base.utils.StringUtils;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDGenerator;
import com.ucsmy.jindu.manage.manage.entity.ManagePermission;
import com.ucsmy.jindu.manage.manage.service.ManagePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限管理
 * Created by
 * @author  chenqilin on 2017/4/14.
 */
@RestController
@RequestMapping("permission")
public class ManagePermissionController {

    private final String module_id_empty = "菜单ID不能为空";
    private final String empty_data = "暂无数据";
    private final String comm_success = "操作成功";

    private final String add_fail = "添加权限失败，请检查网络";
    private final String add_success = "添加权限成功";

    private final String permission_id_empty = "权限ID不能为空";
    private final String data_error = "权限数据不存在";
    private final String update_fail = "更新权限失败，请检查网络";
    private final String update_success = "更新权限成功";

    private final String delete_fail = "删除权限失败，请检查网络";
    private final String delete_success = "删除权限成功";

    @Autowired
    private ManagePermissionService managePermissionService;

    @RequestMapping("list")
    public AosResult queryPermissionListByModuleId(String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(module_id_empty);
        }
        List<ManagePermission> permissions = managePermissionService.queryPermissionByModuleID(id);
        if (permissions == null || permissions.isEmpty()) {
            return AosResult.retFailureMsg(empty_data);
        }
        return AosResult.retSuccessMsg(comm_success, permissions);
    }

    @RequestMapping("add")
    public AosResult addPermission(ManagePermission permission) {
        AosResult validate = managePermissionService.validatePermission(permission);
        if (!String.valueOf(ResultConst.SUCCESS).equals(validate.getRetcode())) {
            return validate;
        }
        permission.setPermissionId(UUIDGenerator.generate());
        int resultCode = managePermissionService.addPermission(permission);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(add_fail);
        }
        return AosResult.retSuccessMsg(add_success, null);
    }

    @RequestMapping("update")
    public AosResult updatePermission(ManagePermission permission) {
        if (StringUtils.isEmpty(permission.getPermissionId())) {
            return AosResult.retFailureMsg(permission_id_empty);
        }
        AosResult validate = managePermissionService.validatePermission(permission);
        if (!String.valueOf(ResultConst.SUCCESS).equals(validate.getRetcode())) {
            return validate;
        }
        ManagePermission existPermission = managePermissionService.getPermissionById(permission.getPermissionId());
        if (existPermission == null) {
            return AosResult.retFailureMsg(data_error);
        }
        int resultCode = managePermissionService.updatePermission(permission);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(update_fail);
        }
        return AosResult.retSuccessMsg(update_success, null);
    }

    @RequestMapping("delete")
    public AosResult deletePermission(String permissionId) {
        if (StringUtils.isEmpty(permissionId)) {
            return AosResult.retFailureMsg(permission_id_empty);
        }
        int resultCode = managePermissionService.deletePermissionByID(permissionId);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(delete_fail);
        }
        return AosResult.retSuccessMsg(delete_success, null);
    }
}
