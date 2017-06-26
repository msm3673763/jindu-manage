package com.ucsmy.jindu.manage.manage.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import com.ucsmy.jindu.manage.manage.ext.MemberProfilePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.aop.exception.BusinessException;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.EncryptUtils;
import com.ucsmy.jindu.manage.commons.base.utils.HibernateValidateUtils;
import com.ucsmy.jindu.manage.commons.base.utils.StringAndNumberUtil;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDGenerator;
import com.ucsmy.jindu.manage.manage.dao.ManageUserProfileMapper;
import com.ucsmy.jindu.manage.manage.entity.ManageRole;
import com.ucsmy.jindu.manage.manage.entity.ManageUserAccount;
import com.ucsmy.jindu.manage.manage.entity.ManageUserProfile;
import com.ucsmy.jindu.manage.manage.entity.ManageUserRole;
import com.ucsmy.jindu.manage.manage.ext.UserProfilePojo;
import com.ucsmy.jindu.manage.manage.service.ManageRoleService;
import com.ucsmy.jindu.manage.manage.service.ManageUserAccountService;
import com.ucsmy.jindu.manage.manage.service.ManageUserProfileService;
import com.ucsmy.jindu.manage.manage.service.ManageUserRoleService;
/***
 * 
 * @author ucs_hexuejun
 *
 */
@Service
public class ManageUserProfileServiceImpl implements ManageUserProfileService {
	@Autowired
	private ManageUserProfileMapper manageUserProfileMapper;
	@Autowired
	private ManageUserAccountService manageUserAccountService;
	@Autowired
	private ManageRoleService manageRoleService;
	@Autowired
	private ManageUserRoleService manageUserRoleService;

	@Override
	@Logger(printSQL = true)
	public PageResult<ManageUserProfile> queryUserProfile(String name, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return manageUserProfileMapper.queryUserProfile(name,new PageRequest(pageNum,pageSize));
	}

	@Override
	@Logger(printSQL = true)
	public UserProfilePojo queryUserProfileByUserId(String userId) {
		// TODO Auto-generated method stub
		return manageUserProfileMapper.queryUserProfileByUserId(userId);
	}

	@Override
	@Logger(printSQL = true)
	public ManageUserProfile queryUserProfileByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return manageUserProfileMapper.queryUserProfileByLoginName(loginName);
	}

	@Override
	@Logger(printSQL = true)
	public int chenckUserProfileByMobilephone(String mobilephone) {
		// TODO Auto-generated method stub
		return manageUserProfileMapper.chenckUserProfileByMobilephone(mobilephone);
	}

	@Override
	@Logger(printSQL = true)
	public int chenckUserProfileByEmail(String email) {
		// TODO Auto-generated method stub
		return manageUserProfileMapper.chenckUserProfileByEmail(email);
	}

	@Override
	@Logger(printSQL = true)
	public int saveUserProfile(ManageUserProfile manageUserProfile) {
		// TODO Auto-generated method stub
		return manageUserProfileMapper.saveUserProfile(manageUserProfile);
	}

	@Override
	@Logger(printSQL = true)
	public int updateUserProfile(ManageUserProfile manageUserProfile) {
		// TODO Auto-generated method stub
		return manageUserProfileMapper.updateUserProfile(manageUserProfile);
	}

	@Override
	@Logger(printSQL = true)
	public int deleteUserProfile(String userId) {
		// TODO Auto-generated method stub
		return manageUserProfileMapper.deleteUserProfile(userId);
	}

	@Override
	@Logger(printSQL = true)
	public AosResult add(ManageUserProfile profile, ManageUserAccount userAccount) {
		profile.setBirthday("1990-01-01");
		profile.setType("register");
		profile.setCreateDate(new Timestamp(System.currentTimeMillis()));
		profile.setUpdateDate(profile.getCreateDate());

		userAccount.setCreateTime(profile.getCreateDate());
		userAccount.setStatus((byte) 0);
		userAccount.setSalt(StringAndNumberUtil.getRandom(5));
		profile.setUserAccount(userAccount);
		String id = UUIDGenerator.generate();
		profile.setUserId(id);
		userAccount.setUserId(id);

		// 加盐加密
		String password = "ucsmy123456";
		password = EncryptUtils.mD5(userAccount.getAccount().concat(password).concat(userAccount.getSalt()));
		userAccount.setPassword(password);

		String errorMsg = HibernateValidateUtils.getErrors(userAccount, profile);
		if(!StringAndNumberUtil.isNull(errorMsg)) {
			throw new BusinessException(errorMsg);
		}

		if(manageUserAccountService.chenckUserAccountByAccount(userAccount.getAccount()) > 0) {
			return AosResult.retFailureMsg("帐号已存在");
		}
		if(chenckUserProfileByMobilephone(profile.getMobilephone()) > 0) {
			return AosResult.retFailureMsg("手机号码已存在");
		}
		if(chenckUserProfileByMobilephone(profile.getEmail()) > 0) {
			return AosResult.retFailureMsg("手机号码已存在");
		}

		ManageRole role = manageRoleService.queryRoleById(profile.getRole().getRoleId());
		if(role == null) {
			return AosResult.retFailureMsg("角色不存在");
		}
		saveUserProfile(profile);
		manageUserAccountService.saveUserAccount(userAccount);
		ManageUserRole userRole = new ManageUserRole();
		userRole.setId( UUID.randomUUID().toString());
		userRole.setUserId(profile.getUserId());
		userRole.setRole( role);
		manageUserRoleService.insertUserRole(userRole);
		return AosResult.retSuccessMsg("新增成功");
	}

	@Override
	@Logger(printSQL = true)
	public AosResult update(ManageUserProfile profile, ManageUserAccount userAccount) {
		if(manageUserAccountService.queryUserAccountByUserId(userAccount.getUserId()) == null) {
			return AosResult.retFailureMsg("不存在该用户数据");
		}
		String errorMsg = HibernateValidateUtils.getErrors(userAccount, profile);
		if(!StringAndNumberUtil.isNull(errorMsg)) {
			return AosResult.retFailureMsg(errorMsg);
		}
		ManageRole role = manageRoleService.queryRoleById(profile.getRole().getRoleId());
		if(role == null) {
			return AosResult.retFailureMsg("角色不存在");
		}
		manageUserAccountService.updateUserInfo(profile,userAccount,role);
		return AosResult.retSuccessMsg("修改成功");
	}

	@Override
	@Logger(printSQL = true)
	public AosResult delete(String userId) {
		ManageUserProfile up = queryUserProfileByUserId(userId);
		if(up == null) {
			return AosResult.retFailureMsg("不存在该用户数据");

		}
		deleteUserProfile(up.getUserId());
		manageUserAccountService.deleteUserAccount(up.getUserAccount().getUserId());
		return AosResult.retSuccessMsg("删除成功");
	}

	@Override
	public PageResult<MemberProfilePojo> getMemberProfilePojo(String name, int pageNum, int pageSize) {
		return manageUserProfileMapper.getMemberProfilePojo(name,new PageRequest(pageNum,pageSize));
	}
}
