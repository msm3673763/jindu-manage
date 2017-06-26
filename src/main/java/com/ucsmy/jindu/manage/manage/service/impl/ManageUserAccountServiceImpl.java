package com.ucsmy.jindu.manage.manage.service.impl;

//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.EncryptUtils;
import com.ucsmy.jindu.manage.manage.dao.ManageUserAccountMapper;
import com.ucsmy.jindu.manage.manage.dao.ManageUserOauth2RelMapper;
import com.ucsmy.jindu.manage.manage.entity.ManageRole;
import com.ucsmy.jindu.manage.manage.entity.ManageUserAccount;
import com.ucsmy.jindu.manage.manage.entity.ManageUserOauth2Rel;
import com.ucsmy.jindu.manage.manage.entity.ManageUserProfile;
import com.ucsmy.jindu.manage.manage.entity.ManageUserRole;
//import com.ucsmy.jindu.manage.manage.entity.*;
import com.ucsmy.jindu.manage.manage.service.ManageUserAccountService;
import com.ucsmy.jindu.manage.manage.service.ManageUserProfileService;
import com.ucsmy.jindu.manage.manage.service.ManageUserRoleService;
/**
 * 
 * @author ucs_hexuejun
 *
 */
@Service
public class ManageUserAccountServiceImpl implements ManageUserAccountService {

    @Autowired
    protected ManageUserOauth2RelMapper manageUserOauth2RelMapper;
    @Autowired
    private ManageUserAccountMapper manageUserAccountMapper;
    @Autowired
    private ManageUserAccountService manageUserAccountService;
    @Autowired
    private ManageUserProfileService manageUserProfileService;
    @Autowired
    private ManageUserRoleService manageUserRoleService;

    @Override
    @Logger(printSQL = true)
    public ManageUserAccount findByUserName(String userName) {
        return manageUserAccountMapper.findByUserName(userName);
    }

    @Override
    @Logger(printSQL = true)
    public ManageUserAccount queryUserAccount(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        return manageUserAccountMapper.queryUserAccount(map);
    }

    @Override
    @Logger(printSQL = true)
    public ManageUserAccount queryUserAccountByUserId(String userId) {
        // TODO Auto-generated method stub
        return manageUserAccountMapper.queryUserAccountByUserId(userId);
    }

    @Override
    @Logger(printSQL = true)
    public int chenckUserAccountByAccount(String account) {
        // TODO Auto-generated method stub
        return manageUserAccountMapper.chenckUserAccountByAccount(account);
    }

    @Override
    @Logger(printSQL = true)
    public int saveUserAccount(ManageUserAccount manageUserAccount) {
        // TODO Auto-generated method stub
        return manageUserAccountMapper.saveUserAccount(manageUserAccount);
    }

    @Override
    @Logger(printSQL = true)
    public int updateUserAccount(ManageUserAccount manageUserAccount) {
        // TODO Auto-generated method stub
        return manageUserAccountMapper.updateUserAccount(manageUserAccount);
    }

    @Override
    @Logger(printSQL = true)
    public AosResult updateUserAccountPassword(String userId, String password, String newPassword) {

        if (StringUtils.isBlank(password) || StringUtils.isBlank(newPassword)) {
            return AosResult.retFailureMsg("密码不能为空");
        }

        if (!password.equals(newPassword)) {
            return AosResult.retFailureMsg("两次密码输入不一致");
        }
        ManageUserAccount account = manageUserAccountMapper.queryUserAccountByUserId(userId);
        if (account == null) {
            return AosResult.retFailureMsg("不存在该用户数据");
        }
        account.setPassword(EncryptUtils.mD5(account.getAccount().concat(password).concat(account.getSalt())));
        manageUserAccountMapper.updateUserAccountPassword(account);
        return AosResult.retSuccessMsg("修改密码成功", null);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteUserAccount(String userId) {
        // TODO Auto-generated method stub
        return manageUserAccountMapper.deleteUserAccount(userId);
    }

    @Override
    @Logger(printSQL = true)
    public void updateUserInfo(ManageUserProfile profile, ManageUserAccount userAccount, ManageRole role) {
        //更新用户信息
        manageUserAccountService.updateUserAccount(userAccount);
        //更新用户帐号信息
        manageUserProfileService.updateUserProfile(profile);

        // 删除用户对应角色
        manageUserRoleService.deleteUserRoleByIds(profile.getUserId());
        //用户新角色绑定
        ManageUserRole userRole = new ManageUserRole();
        userRole.setId(UUID.randomUUID().toString());
        userRole.setUserId(profile.getUserId());
        userRole.setRole(role);
        manageUserRoleService.insertUserRole(userRole);
    }

    @Override
    @Logger(printSQL = true)
    public String getAccountByOprenid(String openid) {
        ManageUserOauth2Rel manageUserOauth2Rel = manageUserOauth2RelMapper.selectByOpenid(openid);
        if (manageUserOauth2Rel != null) {
            ManageUserAccount manageUserAccount = manageUserAccountMapper.queryUserAccountByUserId(manageUserOauth2Rel.getUserId());
            if (manageUserAccount != null) {
            	return manageUserAccount.getAccount();
            }
        }
        return "";
    }

    @Override
    @Logger(printSQL = true)
    public boolean isBindedOauthByUserId(String username) {

        ManageUserOauth2Rel manageUserOauth2Rel = manageUserOauth2RelMapper.selectByUsername(username);
        if(manageUserOauth2Rel!=null){
            return true;
        }
          return false;
       
    }

    @Override
    public int isExistMobileWithUserId(String mobile, String userId) {
        return manageUserAccountMapper.isExistMobileWithUserId(mobile, userId);
    }

    @Override
    public int isExistEmailWithUserId(String email, String userId) {
        return manageUserAccountMapper.isExistEmailWithUserId(email, userId);
    }

}
