package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.jindu.manage.manage.ext.MemberProfilePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.ManageUserProfile;
import com.ucsmy.jindu.manage.manage.ext.UserProfilePojo;
/***
 * 
 * @author admin
 *
 */
@Mapper
public interface ManageUserProfileMapper {
	
	PageResult<ManageUserProfile> queryUserProfile(@Param("name") String name, PageRequest pageRequest);
	
	UserProfilePojo queryUserProfileByUserId(@Param("userId") String userId);
	
	ManageUserProfile queryUserProfileByLoginName(@Param("loginName") String loginName);
	
	int chenckUserProfileByMobilephone(@Param("mobilephone") String mobilephone);
	
	int chenckUserProfileByEmail(@Param("email") String email);
	
	int saveUserProfile( ManageUserProfile manageUserProfile);
	
	int updateUserProfile(ManageUserProfile manageUserProfile);
	
	int deleteUserProfile(@Param("userId") String userId);

	PageResult<MemberProfilePojo> getMemberProfilePojo(@Param("name")String name, PageRequest pageRequest);
}
