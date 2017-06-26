package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.jindu.manage.manage.entity.ManageUserOauth2Rel;
import org.apache.ibatis.annotations.Mapper;
/***
 * 
 * @author admin
 *
 */
@Mapper
public interface ManageUserOauth2RelMapper {


    int insert(ManageUserOauth2Rel record);



    ManageUserOauth2Rel selectByOpenid(String openid);

    ManageUserOauth2Rel selectByUsername(String username);
}
