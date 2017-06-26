package com.ucsmy.jindu.manage.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.manage.dao.ManageConfigMapper;
import com.ucsmy.jindu.manage.manage.entity.ManageConfig;
import com.ucsmy.jindu.manage.manage.service.ManageConfigService;

/**
 * Created by 
 * @author  ucs_panwenbo on 2017/4/14.
 */
@Service
public class ManageConfigServiceImpl implements ManageConfigService {
    @Autowired
    protected ManageConfigMapper manageConfigMapper;

    @Override
    @Logger(operationType="查询操作",operationName="参数分页", printSQL = true)
    public PageResult<ManageConfig> queryConfig(String paramKey, int page, int size) {
        return manageConfigMapper.queryConfig(paramKey,new PageRequest(page,size));
    }

    @Override
    @Logger(operationType="添加操作",operationName="添加参数", printSQL = true)
    public int addConfig(ManageConfig manageConfig) {
        return manageConfigMapper.addConfig(manageConfig);
    }

    @Override
    @Logger(operationType="修改操作",operationName="修改参数", printSQL = true)
    public int editConfig(ManageConfig manageConfig) {
        return manageConfigMapper.editConfig(manageConfig);
    }

    @Override
    @Logger(operationType="删除操作",operationName="根据id删除参数", printSQL = true)
    public int deleteConfig(String id) {
        return manageConfigMapper.deleteConfig(id);
    }

    @Override
    @Logger(operationType="查询操作",operationName="根据参数id查询参数", printSQL = true)
    public ManageConfig getConfig(String id) {
        return manageConfigMapper.getConfig(id);
    }

    @Override
    @Logger(operationType="查询操作",operationName="根据参数名称查询参数", printSQL = true)
    public ManageConfig queryByName(String paramKey) {
        return manageConfigMapper.queryByName(paramKey);
    }

    @Override
    @Logger(operationType="查询操作",operationName="查询配置是否存在", printSQL = true)
    public int isKeyExist(Map<String, Object> map) {
        return manageConfigMapper.isKeyExist(map);
    }
}
