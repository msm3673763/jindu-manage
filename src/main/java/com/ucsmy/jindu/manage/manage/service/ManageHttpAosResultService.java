package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;

/**
 * Created by 
 * @author  ucs_mojiazhou on 2017/4/28.
 */
public interface ManageHttpAosResultService {

    /*****
     * 发送post jsono格式请求
     * @param url
     * @param json
     * @return
     */
    AosResult sendPostJson(String url,String strJson);

    /**
     * 发送get请求
     * @param url
     * @return
     */
    AosResult sendGet(String url);

}
