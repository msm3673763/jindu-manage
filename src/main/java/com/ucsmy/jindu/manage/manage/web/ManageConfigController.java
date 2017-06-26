package com.ucsmy.jindu.manage.manage.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.ucsmy.commons.utils.HibernateValidateUtils;
//import com.ucsmy.commons.utils.StringAndNumberUtil;
//import com.ucsmy.commons.utils.UUIDGenerator;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.HibernateValidateUtils;
import com.ucsmy.jindu.manage.commons.base.utils.StringAndNumberUtil;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDGenerator;
import com.ucsmy.jindu.manage.manage.entity.ManageConfig;
import com.ucsmy.jindu.manage.manage.service.ManageConfigService;

/**
 * Created by 
 * 
 * @author  ucs_panwenbo on 2017/4/13.
 */
@Controller
@RequestMapping("config")
public class ManageConfigController {
    private final String message_duplicate = "已存在相同名称的参数";
    private final String message_add_success = "参数插入成功";
    private final String message_add_fail = "参数插入失败";

    private final String message_edit_success = "修改成功";
    private final String message_edit_fail = "修改失败";
    private final String message_id_not_exist = "参数不存在";
    private final String message_name_exist = "修改后的参数名称已存在";

    private final String message_id_null = "参数的 ID 为空";
    private final String message_success = "删除成功";
    private final String message_del_fail = "删除失败";

    @Autowired
    private ManageConfigService manageConfigService;

    @RequestMapping("queryConfig")
    @ResponseBody
    public PageResult<ManageConfig> queryConfig(@RequestParam(required = false) String paramKey, 
    		@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize){
        return manageConfigService.queryConfig(paramKey,pageNum,pageSize);
    }

    @RequestMapping("getConfig")
    @ResponseBody
    public ManageConfig getConfig(String id){
        return manageConfigService.getConfig(id);
    }

    @RequestMapping("addConfig")
    @ResponseBody
    public AosResult addConfig(ManageConfig manageConfig){
        manageConfig.setId(UUIDGenerator.generate());
        String errorMsg = HibernateValidateUtils.getErrors(manageConfig);
        if(!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult. retFailureMsg(  errorMsg);
        } else {
            if (isParamKeyExist(manageConfig.getParamKey())) {
                return AosResult. retFailureMsg(  message_duplicate);
            } else {
                int insertCount =  manageConfigService.addConfig(manageConfig);
                if (insertCount > 0) {
                    return AosResult.retSuccessMsg(message_add_success, null);
                } else {
                    return AosResult. retFailureMsg(  message_add_fail);
                }
            }
        }
    }

    @RequestMapping("editConfig")
    @ResponseBody
    public AosResult editConfig(ManageConfig manageConfig){
        String errorMsg = HibernateValidateUtils.getErrors(manageConfig);
        if(!StringAndNumberUtil.isNullAfterTrim(errorMsg)) {
            return AosResult. retFailureMsg(  errorMsg);
        } else {
            if(!isIdExist(manageConfig.getId())) {
                return AosResult. retFailureMsg(  message_id_not_exist);
            } else {
                if(isKeyExist(manageConfig.getId(), manageConfig.getParamKey())) {
                    return AosResult. retFailureMsg(  message_name_exist);
                } else {
                    int updateCount =  manageConfigService.editConfig(manageConfig);
                    if(updateCount > 0) {
                        return AosResult.retSuccessMsg(message_edit_success, null);
                    } else {
                        return AosResult. retFailureMsg(  message_edit_fail);
                    }
                }
            }
        }
    }

    @RequestMapping("deleteConfig")
    @ResponseBody
    public AosResult deleteConfig(String id){
        if (StringAndNumberUtil.isNullAfterTrim(id)) {
            return AosResult. retFailureMsg(  message_id_null);
        } else {
            int deleteCount = manageConfigService.deleteConfig(id);
            if (deleteCount > 0) {
                return AosResult.retSuccessMsg(message_success, null);
            } else {
                return AosResult. retFailureMsg(  message_del_fail);
            }
        }
    }

    private boolean isParamKeyExist(String paramKey) {
        ManageConfig config = manageConfigService.queryByName(paramKey);
        if (null != config) {
            return true;
        }
        return false;
    }

    private boolean isIdExist( String id) {
        ManageConfig config = manageConfigService.getConfig(id);
        if(null != config) {
            return true;
        }
        return false;
    }

    private boolean isKeyExist(String id, String paramKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("paramKey", paramKey);
        int count = manageConfigService.isKeyExist(map);
        if(count > 0) {
            return true;
        }
        return false;
    }
}
