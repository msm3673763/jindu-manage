package com.ucsmy.jindu.manage.manage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 
 * @author   chenqilin on 2017/4/14.
 */
@Mapper
public interface ManageRoleModuleMapper {

    int deleteRoleByModuleId(@Param("id") String id);
}
