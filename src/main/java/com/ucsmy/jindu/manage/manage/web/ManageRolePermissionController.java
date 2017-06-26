package com.ucsmy.jindu.manage.manage.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.jindu.manage.manage.service.ManageRolePermissionService;
/***
 * 
 * @author ucs_hexuejun
 *
 */
@Controller
@RequestMapping("rolePermission")
public class ManageRolePermissionController {

	@Autowired
    protected ManageRolePermissionService manageRolePermissionService;
	
	@RequestMapping("queryRolePermission")
	@ResponseBody
	public String queryRolePermission(String role_id) throws JsonProcessingException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rolePermissions", JSONObject.parse(manageRolePermissionService.queryAllModulePermission(role_id)));
		jsonObject.put("success", true);
		jsonObject.put("msg", "");
		return jsonObject.toString();
	}
	
	@RequestMapping("addRolePermission")
	@ResponseBody
	public String addRolePermission(String role_id,String permissions_id,String name) {
		return manageRolePermissionService.addRolePermission(role_id, permissions_id, name);
	}

}
