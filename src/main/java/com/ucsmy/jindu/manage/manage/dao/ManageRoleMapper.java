package com.ucsmy.jindu.manage.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.ManageRole;
/***
 * 
 * @author admin
 *
 */
@Mapper
public interface ManageRoleMapper {
	
	PageResult<ManageRole> queryRoleList(@Param("name") String name, PageRequest pageRequest);
	
	List<ManageRole> queryAllRoleList();
	
	ManageRole queryRoleByName(@Param("name") String name);
	
	ManageRole queryRoleById(@Param("roleId") String roleId);
	
	Long queryRoleUserCountByRoleId(@Param("roleId") String roleId);
	
	String queryRoleMark(@Param("roleId") String roleId);
	
	int addRole(ManageRole manageRole);
	
	int updateRole(ManageRole manageRole);
	
	int deleteRole(@Param("roleId") String roleId);
	
}
