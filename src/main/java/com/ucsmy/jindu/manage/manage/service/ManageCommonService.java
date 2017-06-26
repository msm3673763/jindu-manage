package com.ucsmy.jindu.manage.manage.service;


/**
 * Created by 
 * @author ucs_mojiazhou on 2017/5/11.
 */
public interface ManageCommonService {

     static final String ROOT_URL_KEY = "USER_CORE_URL";

    static final String INTEGRATION_URL = "INTEGRATION_URL";
    /**
     * 地址前添加
     * RootUrl
     * @param url
     * @return
     */
    String concantRootUrl(String url);

    String concantIntegration(String url);

}
