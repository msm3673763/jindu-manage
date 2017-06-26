package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.ManageRole;
import com.ucsmy.jindu.manage.manage.entity.ManageUserAccount;
import com.ucsmy.jindu.manage.manage.entity.ManageUserProfile;

import java.util.HashMap;

/**
 * 
 * @author ucs_hexuejun
 *
 */
public interface ManageUserAccountService {

	ManageUserAccount findByUserName(String userName);

	ManageUserAccount queryUserAccount(HashMap<String, Object> map);

	ManageUserAccount queryUserAccountByUserId(String userId);

	int chenckUserAccountByAccount(String account);

	int saveUserAccount(ManageUserAccount manageUserAccount);

	int updateUserAccount(ManageUserAccount manageUserAccount);

	AosResult updateUserAccountPassword(String userId, String password, String newPassword);

	int deleteUserAccount(String userId);

	void updateUserInfo(ManageUserProfile profile, ManageUserAccount userAccount, ManageRole role );

	String  getAccountByOprenid(String openid);

	boolean isBindedOauthByUserId(String username);


	int isExistMobileWithUserId(String mobile,String userId);

	int isExistEmailWithUserId(String email, String userId);

}
