package com.ucsmy.jindu.manage.manage.web;

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.StringUtils;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDGenerator;
import com.ucsmy.jindu.manage.manage.entity.ManageModule;
import com.ucsmy.jindu.manage.manage.ext.ModuleTreePojo;
import com.ucsmy.jindu.manage.manage.service.ManageModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import org.springframework.web.bind.annotation.*;
//import com.ucsmy.commons.utils.StringUtils;
//import com.ucsmy.commons.utils.UUIDGenerator;

/**
 * Created by 
 * 菜单管理
 * @author   chenqilin on 2017/4/13.
 */
@RestController
@RequestMapping("module")
public class ManageModuleController {

    private final String parent_id_empty = "菜单父级ID不能为空";
    private final String add_data_empty = "此菜单没有数据，不能添加子节点！";
    private final String add_fail = "添加菜单失败，请检查网络";
    private final String add_success = "添加成功";

    private final String id_empty = "菜单ID不能为空";
    private final String update_data_empty = "数据库中无菜单项！修改失败";
    private final String update_fail = "更新菜单失败，请检查网络";
    private final String update_success = "修改成功";

    private final String delete_data_empty = "数据库中无菜单项！删除失败";
    private final String has_children = "此菜单存在子节点，不能删除";
    private final String delete_fail = "删除菜单失败，请检查网络";
    private final String delete_success = "删除成功";
    private final static String HAS_SAME_NAME = "该父节点下存在同名子节点";

    @Autowired
    private ManageModuleService manageModuleService;

    @RequestMapping("list")
    public AosResult getModuleList() {
        List<ModuleTreePojo> resultList = manageModuleService.getModuleListByCondition("");
        return AosResult.retSuccessMsg("success", resultList);
    }

    @RequestMapping("add")
    public AosResult addModule(ManageModule module) {
        if (StringUtils.isEmpty(module.getParentId())) {
            return AosResult.retFailureMsg(parent_id_empty);
        }
        ModuleTreePojo parentModule = manageModuleService.getModuleDetail(module.getParentId());
        if (parentModule == null) {
            return AosResult.retFailureMsg(add_data_empty);
        }
        module.setModuleId(UUIDGenerator.generate());
        List<ModuleTreePojo> resultList = manageModuleService.getModuleListByCondition(module.getName()
                , module.getParentId(), module.getModuleId());
        if (!resultList.isEmpty()) {
            return AosResult.retFailureMsg(HAS_SAME_NAME);
        }

        int resultCode = manageModuleService.addModule(module);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(add_fail);
        }
        return AosResult.retSuccessMsg(add_success, null);
    }

    @RequestMapping("update")
    public AosResult updateModule(ManageModule module, String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(id_empty);
        }
        ModuleTreePojo oldModule = manageModuleService.getModuleDetail(id);
        if (oldModule == null) {
            return AosResult.retFailureMsg(update_data_empty);
        }
        module.setModuleId(id);
        int resultCode = manageModuleService.updateModule(module);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(update_fail);
        }
        return AosResult.retSuccessMsg(update_success, null);
    }

    @RequestMapping("delete")
    public AosResult deleteModule(String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(id_empty);
        }
        ModuleTreePojo module = manageModuleService.getModuleDetail(id);
        if (module == null) {
            return AosResult.retFailureMsg(delete_data_empty);
        }
        int childNum = manageModuleService.getChildrenNum(id);
        if (childNum > 0) {
            return AosResult.retFailureMsg(has_children);
        }
        int resultCode = manageModuleService.deleteModule(id);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(delete_fail);
        }
        return AosResult.retSuccessMsg(delete_success, null);
    }

}
