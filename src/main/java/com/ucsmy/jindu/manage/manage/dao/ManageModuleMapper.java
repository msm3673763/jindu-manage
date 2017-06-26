package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.jindu.manage.manage.entity.ManageModule;
import com.ucsmy.jindu.manage.manage.ext.MainModulePojo;
import com.ucsmy.jindu.manage.manage.ext.ModuleTreePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ucas_client_module
 * Created by 
 * @author  chenqilin on 2017/4/13.
 */
@Mapper
public interface ManageModuleMapper {

    List<ModuleTreePojo> getModuleList(@Param("name") String name, @Param("parentId") String parentId);

    ModuleTreePojo getModuleDetail(@Param("id") String id);

    int addModule(ManageModule module);

    int updateModule(ManageModule module);

    int deleteModule(@Param("id") String id);

    int getChildrenNum(@Param("id") String id);

    List<MainModulePojo> queryMainAllModule();

    List<MainModulePojo> queryMainModuleByUserId(@Param("userId") String userId);

    List<ModuleTreePojo> getModuleListByMap(Map<String, String> map);
}
