package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.jindu.manage.manage.entity.ManagePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ucas_client_permission
 * Created by 
 * @author    chenqilin on 2017/4/14.
 */
@Mapper
public interface ManagePermissionMapper {

    List<ManagePermission> queryPermissionByModuleID(@Param("id") String id);

    ManagePermission getPermissionById(@Param("permissionId") String permissionId);

    int updatePermission(ManagePermission permission);

    int deletePermissionByID(@Param("permissionId") String permissionId);

    int deletePermissionByModule(@Param("id") String id);

    int addPermission(ManagePermission permission);

    List<ManagePermission> queryPermissionAll();

    List<ManagePermission> queryPermissionByUserID(@Param("userId") String userId);

    List<ManagePermission> queryPermissionByCondition(@Param("moduleId") String moduleId,
                @Param("name") String name, @Param("permissionId") String permissionId);

    ManagePermission queryPermissionByMap(Map<String, String> map);
}
