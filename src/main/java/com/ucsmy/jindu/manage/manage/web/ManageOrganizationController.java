package com.ucsmy.jindu.manage.manage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.ucsmy.commons.tree.TreeTool;
//import com.ucsmy.commons.utils.HibernateValidateUtils;
//import com.ucsmy.commons.utils.StringAndNumberUtil;
//import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.interceptor.tree.TreeTool;
import com.ucsmy.jindu.manage.commons.base.utils.HibernateValidateUtils;
import com.ucsmy.jindu.manage.commons.base.utils.StringAndNumberUtil;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDGenerator;
import com.ucsmy.jindu.manage.manage.entity.ManageOrganization;
import com.ucsmy.jindu.manage.manage.ext.UcasClientOrganizationUser;
import com.ucsmy.jindu.manage.manage.ext.UcasClientProfileOrganization;
import com.ucsmy.jindu.manage.manage.service.ManageOrganizationService;

/**
 * Created by 
 * @author  ucs_panwenbo on 2017/4/14.
 */
@Controller
@RequestMapping("organization")
public class ManageOrganizationController {
    @Autowired
    protected ManageOrganizationService manageOrganizationService;

    private final String message_check_success = "数据查找成功";
    private final String message_check_fail = "数据查找失败";
    private final String message_zero = "没有组织数据";

    private final String message_success = "数据删除成功";
    private final String message_fail = "数据删除失败";
    private final String message_zj_id_null = "组织 ID 为空";
    private final String message_exists_children = "该节点存在下级子节点";
    private final String message_bind_success = "成功绑定";
    private final String message_organization_id_null = "组织 ID 为空";
    private final String message_uesr_ids = "没有选择用户";

    private final String message_id_null = "ID 为空";


    //组织列表业务逻辑
    @RequestMapping("queryOrganization")
    @ResponseBody
    public AosResult queryOrganization() throws JsonProcessingException {
        List<ManageOrganization> manageOrganizationList = manageOrganizationService.getOrganizationList();
        if (null == manageOrganizationList) {
            return AosResult. retFailureMsg(  message_check_fail);
        } else if (manageOrganizationList.isEmpty()) {
            return AosResult.retSuccessMsg(message_zero, TreeTool.getTreeList(manageOrganizationList));
        } else {
            return AosResult.retSuccessMsg(message_check_success, TreeTool.getTreeList(manageOrganizationList));
        }
    }

    //组织详细业务逻辑
    @RequestMapping("getOrganization")
    @ResponseBody
    public AosResult getOrganization(String id) throws JsonProcessingException {
        if (StringAndNumberUtil.isNullAfterTrim(id)) {
            return AosResult. retFailureMsg(  message_id_null, null);
        } else {
            ManageOrganization organization = manageOrganizationService.getOrganizationById(id);
            if (null == organization) {
                return AosResult. retFailureMsg(  message_fail, null);
            } else {
                return AosResult.retSuccessMsg(message_success, organization);
            }
        }
    }

    //组织更新业务逻辑
    @RequestMapping("editOrganization")
    @ResponseBody
    public AosResult editOrganization(ManageOrganization manageOrganization) {
        String errorMsg = HibernateValidateUtils.getErrors(manageOrganization);
        if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult. retFailureMsg(  errorMsg);
        } else {
            if (StringAndNumberUtil.isNullAfterTrim(manageOrganization.getId())) {
                return AosResult. retFailureMsg(  message_zj_id_null);
            } else {
                int updateCount = manageOrganizationService.updateOrganization(manageOrganization);
                if (updateCount > 0) {
                    return AosResult.retSuccessMsg("数据更新成功", null);
                } else {
                    return AosResult. retFailureMsg(  "数据更新失败");
                }
            }
        }
    }

    //组织添加业务逻辑
    @RequestMapping("addOrganization")
    @ResponseBody
    public AosResult addOrganization(ManageOrganization manageOrganization) {
        String errorMsg = HibernateValidateUtils.getErrors(manageOrganization);
        if (!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult. retFailureMsg(  errorMsg);
        } else {
            manageOrganization.setId(UUIDGenerator.generate());
            int insertCount = manageOrganizationService.insertOrganization(manageOrganization);
            if (insertCount > 0) {
                return AosResult.retSuccessMsg("数据插入成功", null);
            } else {
                return AosResult. retFailureMsg(  "数据插入失败");
            }
        }
    }

    //组织删除业务逻辑
    @RequestMapping("deleteOrganization")
    @ResponseBody
    public AosResult deleteOrganization(String id) {
        if (this.isExistChildren(id)) {
            return AosResult. retFailureMsg(  message_exists_children);
        } else {
            int deleteCount = manageOrganizationService.deleteOrganization(id);
            if (deleteCount > 0) {
                return AosResult.retSuccessMsg(message_success, null);
            } else {
                return AosResult.retSuccessMsg(message_fail, null);
            }
        }
    }

    //组织绑定业务逻辑
    @RequestMapping("bindOrganization")
    @ResponseBody
    public AosResult bindOrganization(String organizationId, String userId) {
        if (StringAndNumberUtil.isNullAfterTrim(organizationId)) {
            return AosResult. retFailureMsg(  message_organization_id_null);
        } else {
            if (StringAndNumberUtil.isNullAfterTrim(userId)) {
                return AosResult. retFailureMsg(  message_uesr_ids);
            } else {
                this.deleteBatchByUserIds(userId);
                this.insertBatch(userId, organizationId);
                return AosResult.retSuccessMsg(message_bind_success, null);
            }
        }
    }

    //组织解绑业务逻辑
    @RequestMapping("unbindOrganization")
    @ResponseBody
    public AosResult unbindOrganization(String organizationId, String userId) {
        if (StringAndNumberUtil.isNullAfterTrim(organizationId)) {
            return AosResult. retFailureMsg(  "组织 ID 为空");

        } else {
            if (StringAndNumberUtil.isNullAfterTrim(userId)) {
                return AosResult. retFailureMsg(  "没有选择用户");
            } else {
                this.deleteBatch(userId, organizationId);
                return AosResult.retSuccessMsg("成功解绑", null);
            }
        }
    }

    //绑定组织的用户业务逻辑
    @RequestMapping("queryUserWithOrganization")
    @ResponseBody
    public PageResult<UcasClientProfileOrganization> queryUserWithOrganization(@RequestParam(required = true) String id, 
    		@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
        return manageOrganizationService.queryUserWithOrganization(id, pageNum, pageSize);
    }

    //没有绑定组织的用户业务逻辑
    @RequestMapping("queryUserWithoutOrganization")
    @ResponseBody
    public PageResult<UcasClientProfileOrganization> queryUserWithoutOrganization(@RequestParam(required = true) String id, 
    		@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize) {
        return manageOrganizationService.queryUserWithoutOrganization(id, pageNum, pageSize);
    }

    private void deleteBatch(String userIds, String organizationId) {
        String[] userIdArray = userIds.split(",");
        List<UcasClientOrganizationUser> list = new ArrayList<>();
        UcasClientOrganizationUser organizationUser;
        for (String userId : userIdArray) {
            organizationUser = new UcasClientOrganizationUser();
            organizationUser.setId(UUIDGenerator.generate());
            organizationUser.setUserId(userId);
            organizationUser.setOrganizationId(organizationId);
            list.add(organizationUser);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("organizationId", organizationId);
        manageOrganizationService.deleteBatch(map);
    }

    private void deleteBatchByUserIds(String userIds) {
        String[] userIdArray = userIds.split(",");
        List<UcasClientOrganizationUser> list = new ArrayList<>();
        UcasClientOrganizationUser organizationUser;
        for (String userId : userIdArray) {
            organizationUser = new UcasClientOrganizationUser();
            organizationUser.setId(UUIDGenerator.generate());
            organizationUser.setUserId(userId);
            list.add(organizationUser);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        manageOrganizationService.deleteBatchByUserIds(map);
    }

    private void insertBatch(String userIds, String organizationId) {
        String[] userIdArray = userIds.split(",");
        List<UcasClientOrganizationUser> list = new ArrayList<>();
        UcasClientOrganizationUser organizationUser;
        for (String userId : userIdArray) {
            organizationUser = new UcasClientOrganizationUser();
            organizationUser.setId(UUIDGenerator.generate());
            organizationUser.setUserId(userId);
            organizationUser.setOrganizationId(organizationId);
            list.add(organizationUser);
        }
        manageOrganizationService.insertBatch(list);
    }

    private boolean isExistChildren(String id) {
        Integer count = manageOrganizationService.countChildren(id);
        if (null == count || count <= 0) {
            return false;
        }
        return true;
    }
}
