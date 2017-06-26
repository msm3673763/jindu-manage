package com.ucsmy.jindu.manage.manage.service;

import java.util.List;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.ManageRole;
/***
 * 
 * @author ucs_hexuejun
 *
 */
public interface ManageRoleService {
	
	PageResult<ManageRole> queryRoleList(String name, int pageNum, int pageSize);
	
	List<ManageRole> queryAllRoleList();
	
	ManageRole queryRoleByName(String name);
	
	ManageRole queryRoleById(String roleId);
	
	Long queryRoleUserCountByRoleId(String roleId);
	
	String queryRoleMark(String roleId);
	
	AosResult addRole(ManageRole manageRole);
	
	AosResult updateRole(ManageRole manageRole);
	
	AosResult deleteRole(String roleId);
	
}
