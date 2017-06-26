package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.ManageConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by 
 * @author  ucs_panwenbo on 2017/4/14.
 */
@Mapper
public interface ManageConfigMapper {
    PageResult<ManageConfig> queryConfig(@Param("paramKey") String  paramKey, PageRequest pageRequest);

    int addConfig(ManageConfig manageConfig);

    int editConfig(ManageConfig manageConfig);

    int deleteConfig(String id);

    ManageConfig getConfig(String id);

    ManageConfig queryByName(String paramKey);

    int isKeyExist(Map<String, Object> map);

    String getValueByKey(@Param("paramKey") String paramKey);
}
