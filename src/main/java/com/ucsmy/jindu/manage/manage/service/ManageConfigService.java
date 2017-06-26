package com.ucsmy.jindu.manage.manage.service;

import java.util.Map;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.ManageConfig;

/**
 * Created by 
 *@author ucs_panwenbo on 2017/4/14.
 */
public interface ManageConfigService {

	PageResult<ManageConfig> queryConfig(String paramKey, int page, int size);

    int addConfig(ManageConfig manageConfig);

    int editConfig(ManageConfig manageConfig);

    int deleteConfig(String id);

    ManageConfig getConfig(String id);

    ManageConfig queryByName(String paramKey);

    int isKeyExist(Map<String, Object> map);

}


