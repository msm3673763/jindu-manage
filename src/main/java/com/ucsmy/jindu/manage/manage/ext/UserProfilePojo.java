package com.ucsmy.jindu.manage.manage.ext;

import com.ucsmy.jindu.manage.manage.entity.ManageOrganization;
import com.ucsmy.jindu.manage.manage.entity.ManageRole;
import com.ucsmy.jindu.manage.manage.entity.ManageUserAccount;
import com.ucsmy.jindu.manage.manage.entity.ManageUserProfile;

/**
 * userProfile扩展类
 * Created by 
 * @author  chenqilin on 2017/4/17.
 */
public class UserProfilePojo extends ManageUserProfile {

    private ManageUserAccount userAccount;

    private ManageRole role;

    private ManageOrganization org;

    public ManageUserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(ManageUserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public ManageRole getRole() {
        return role;
    }

    public void setRole(ManageRole role) {
        this.role = role;
    }

    public ManageOrganization getOrg() {
        return org;
    }

    public void setOrg(ManageOrganization org) {
        this.org = org;
    }
}
