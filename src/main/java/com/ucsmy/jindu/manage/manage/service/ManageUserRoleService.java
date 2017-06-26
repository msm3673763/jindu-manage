package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.ManageUserRole;

import java.util.Map;

/**
 * 
 * @author ucs_hexuejun
 *
 */
public interface ManageUserRoleService {
	
	PageResult<ManageUserRole> queryUserRoleList(String roleId, String name, String type, String account, int pageNum, int pageSize);
	
	PageResult<ManageUserRole> queryUnbindUserList(String roleId, String type, String name, String account, int pageNum, int pageSize);
	
	int queryUserCountByUserIds(Map<String, Object> map);	
	
	int insertUserRole(ManageUserRole manageUserRole);
	
	int deleteUserRoleByUserIds(String[] ids);
	
	int deleteUserRoleByIds(String id);

	AosResult insertUserRole(String roleId, String userIds);
	
	AosResult deleteUserRoles(String ids);
	
}
