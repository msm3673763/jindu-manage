package com.ucsmy.jindu.manage.manage.service.impl;

import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.manage.entity.ManageConfig;
import com.ucsmy.jindu.manage.manage.service.ManageCommonService;
import com.ucsmy.jindu.manage.manage.service.ManageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 
 * @author ucs_mojiazhou on 2017/5/11.
 */
@Service
public class ManageCommonServiceImpl implements ManageCommonService {


    @Autowired
    private ManageConfigService manageConfigService;

    @Logger(printSQL = true)
    private String getRootUrl() {
        ManageConfig manageConfig = manageConfigService.queryByName(ROOT_URL_KEY);
        if (null!=manageConfig){
            return manageConfig.getParamValue();
        }
        return null;
    }

    @Override
    public String concantRootUrl(String url) {
       return getRootUrl()+"/"+url;

    }

    @Override
    public String concantIntegration(String url) {
        ManageConfig manageConfig = manageConfigService.queryByName(INTEGRATION_URL);
        if (null!=manageConfig){
            return manageConfig.getParamValue()+url;
        }
        return null;
    }
}
