package com.ucsmy.jindu.manage.manage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.ManageUserRole;
/***
 * 
 * @author admin
 *
 */
@Mapper
public interface ManageUserRoleMapper {

	PageResult<ManageUserRole> queryUserRoleList(@Param("roleId") String roleId, @Param("name") String name,
                                                   @Param("account") String account, PageRequest pageRequest);

	PageResult<ManageUserRole> queryUnbindUserList(@Param("roleId") String roleId, @Param("name") String name,
                                                     @Param("account") String account, PageRequest pageRequest);

	PageResult<ManageUserRole> queryUnbindUsers(@Param("roleId") String roleId, @Param("name") String name,
					@Param("account") String account, PageRequest pageRequest);

	Long queryUserCountByUserIds(@Param("ids") String[] ids);

	int insertUserRole(ManageUserRole manageUserRole);

	int deleteUserRoleByUserIds(@Param("ids") String[] ids);

	int deleteUserRoleByIds(String id);
	
	int deleteUserRoles(@Param("ids") String[] ids);

	PageResult<ManageUserRole> queryUserRoles(@Param("roleId") String roleId, @Param("name") String name,
											  @Param("account") String account, PageRequest pageRequest);
}
