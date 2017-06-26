package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.ManageUserAccount;
import com.ucsmy.jindu.manage.manage.entity.ManageUserProfile;
import com.ucsmy.jindu.manage.manage.ext.MemberProfilePojo;
import com.ucsmy.jindu.manage.manage.ext.UserProfilePojo;

/***
 * 
 * @author ucs_hexuejun
 *
 */
public interface ManageUserProfileService {

	PageResult<ManageUserProfile> queryUserProfile(String name, int pageNum, int pageSize);

	UserProfilePojo queryUserProfileByUserId(String userId);
	
	ManageUserProfile queryUserProfileByLoginName(String loginName);
	
	int chenckUserProfileByMobilephone(String mobilephone);
	
	int chenckUserProfileByEmail(String email);
	
	int saveUserProfile(ManageUserProfile manageUserProfile);
	
	int updateUserProfile(ManageUserProfile manageUserProfile);
	
	int deleteUserProfile(String userId);

	AosResult add(ManageUserProfile profile, ManageUserAccount userAccount);

	AosResult update(ManageUserProfile profile, ManageUserAccount userAccount);

	AosResult delete(String userId);

	PageResult<MemberProfilePojo> getMemberProfilePojo(String name, int pageNum, int pageSize);
}
