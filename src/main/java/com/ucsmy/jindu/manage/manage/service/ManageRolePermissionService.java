package com.ucsmy.jindu.manage.manage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ucsmy.jindu.manage.manage.entity.ManageRolePermission;

/**
 * Created by 
 * @author  chenqilin on 2017/4/14.
 */
public interface ManageRolePermissionService {

	String queryAllModulePermission(String role_id) throws JsonProcessingException;
	
	ManageRolePermission queryRolePermissionByRoleID();
	
	int insertRolePermissionByBatch();
	
	int insertRoleModuleByBatch();
	
	int deleteRolePermissionByRoleID();
	
	int deleteRoleModuleByRoleID();
	
	String addRolePermission(String role_id,String permissions_id,String name);

}
