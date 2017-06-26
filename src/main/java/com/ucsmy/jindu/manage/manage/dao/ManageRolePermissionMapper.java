package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.jindu.manage.manage.entity.ManageRoleModule;
import com.ucsmy.jindu.manage.manage.entity.ManageRolePermission;
import com.ucsmy.jindu.manage.manage.ext.ModulePermissionPojo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/***
 * 
 * @author admin
 *
 */
@Mapper
public interface ManageRolePermissionMapper {

	List<ModulePermissionPojo> queryAllModulePermission();
	
	List<ManageRolePermission> queryRolePermissionByRoleID(@Param("roleId") String roleId);
	
	int insertRolePermissionByBatch(List<ManageRolePermission> rolePermission_list);
	
	int insertRoleModuleByBatch(List<ManageRoleModule> roleModule_list);
	
	int deleteRolePermissionByRoleID(String role_id);
	
	int deleteRoleModuleByRoleID(String role_id);
}
