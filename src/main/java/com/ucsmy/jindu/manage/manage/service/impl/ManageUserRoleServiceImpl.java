package com.ucsmy.jindu.manage.manage.service.impl;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.StringAndNumberUtil;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDGenerator;
import com.ucsmy.jindu.manage.manage.dao.ManageRoleMapper;
import com.ucsmy.jindu.manage.manage.dao.ManageUserRoleMapper;
import com.ucsmy.jindu.manage.manage.entity.ManageRole;
import com.ucsmy.jindu.manage.manage.entity.ManageUserRole;
import com.ucsmy.jindu.manage.manage.service.ManageUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

//import com.ucsmy.commons.utils.StringAndNumberUtil;
//import com.ucsmy.commons.utils.UUIDGenerator;
/***
 * 
 * @author ucs_hexuejun
 *
 */
@Service
public class ManageUserRoleServiceImpl implements ManageUserRoleService {

	private static final String ROLE_TYPE_0 = "0";//后台角色
	private static final String ROLE_TYPE_1 = "1";//金度角色

	@Autowired
	private ManageUserRoleMapper userRoleMapper;
	@Autowired
	private ManageRoleMapper manageRoleMapper;

	@Override
	@Logger(printSQL = true)
	public PageResult<ManageUserRole> queryUserRoleList(String roleId, String name,
				String type, String account, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (ROLE_TYPE_0.equals(type)) {//后台角色
			return userRoleMapper.queryUserRoleList(roleId, name, account, new PageRequest(pageNum, pageSize));
		} else if (ROLE_TYPE_1.equals(type)) {//金度角色
			return userRoleMapper.queryUserRoles(roleId, name, account, new PageRequest(pageNum, pageSize));
		} else {
			throw new RuntimeException("角色类型不符合标准");
		}
	}

	@Override
	@Logger(printSQL = true)
	public PageResult<ManageUserRole> queryUnbindUserList(String roleId, String type, String name,
				String account, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (ROLE_TYPE_0.equals(type)) {//后台角色
			PageResult<ManageUserRole> p = userRoleMapper.queryUnbindUserList(roleId,
					name, account, new PageRequest(pageNum, pageSize));
			return p;
		} else if (ROLE_TYPE_1.equals(type)) {//金度角色
			return userRoleMapper.queryUnbindUsers(roleId, name,
					account, new PageRequest(pageNum, pageSize));
		} else {
			throw new RuntimeException("角色类型不符合标准！");
		}

	}

	@Override
	@Logger(printSQL = true)
	public int queryUserCountByUserIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		// return userRoleMapper.queryUserCountByUserIds(map);
		return 0;
	}

	@Override
	@Logger(printSQL = true)
	public AosResult insertUserRole(String roleId, String userIds) {

		ManageRole role = manageRoleMapper.queryRoleById(roleId);
		if (role == null) {
			return AosResult.retFailureMsg("操作失败，该角色不存在");
		} else {
			String[] userIdArr = userIds.split(","); // 获得userId数组

			Long userCount = userRoleMapper.queryUserCountByUserIds(userIdArr);
			if (userCount != userIdArr.length) { // 查询的数目和id数目不一致
				return AosResult.retFailureMsg("操作失败，部分用户不存在");
			} else {
				// 先删除原先的绑定数据
				userRoleMapper.deleteUserRoleByUserIds(userIdArr);
				ManageUserRole manageUserRole = new ManageUserRole();
				manageUserRole.setRole(role);
				for (String userId : userIdArr) {
					manageUserRole.setId(UUIDGenerator.generate());
					manageUserRole.setUserId(userId);
					userRoleMapper.insertUserRole(manageUserRole);
				}
				return AosResult.retSuccessMsg("操作成功");
			}
		}
	}

	@Override
	@Logger(printSQL = true)
	public int deleteUserRoleByUserIds(String[] ids) {
		// TODO Auto-generated method stub
		return userRoleMapper.deleteUserRoleByUserIds(ids);
	}

	@Override
	@Logger(printSQL = true)
	public int deleteUserRoleByIds(String id) {
		// TODO Auto-generated method stub
		return userRoleMapper.deleteUserRoleByIds(id);
	}

	@Override
	@Logger(printSQL = true)
	public AosResult deleteUserRoles(String ids) {
		if (StringAndNumberUtil.isNullAfterTrim(ids)) { // 传进来的参数为空
			return AosResult.retFailureMsg("参数错误");
		} else {
			String[] userIdArr = ids.split(",");
			userRoleMapper.deleteUserRoles(userIdArr);
			return AosResult.retSuccessMsg("操作成功");
		}
	}

	@Override
	@Logger(printSQL = true)
	public int insertUserRole(ManageUserRole manageUserRole) {
		// TODO Auto-generated method stub
		return userRoleMapper.insertUserRole(manageUserRole);
	}

}
