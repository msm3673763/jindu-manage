package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.jindu.manage.manage.entity.ManageUserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
/***
 * 
 * @author admin
 *
 */
@Mapper
public interface ManageUserAccountMapper {

	ManageUserAccount findByUserName(@Param("userName") String userName);
	
	ManageUserAccount queryUserAccount(@Param("map") HashMap<String, Object> map);
	
	ManageUserAccount queryUserAccountByUserId(@Param("userId") String userId);
	
	int chenckUserAccountByAccount(@Param("account") String account);
	
	int saveUserAccount(ManageUserAccount manageUserAccount);
	
	int updateUserAccount(ManageUserAccount manageUserAccount);
	
	int updateUserAccountPassword(ManageUserAccount manageUserAccount);
	
	int deleteUserAccount(@Param("userId") String userId);

	int isExistMobileWithUserId(@Param("mobile") String mobile, @Param("userId") String userId);

	int isExistEmailWithUserId(@Param("email") String email, @Param("userId") String userId);
}
