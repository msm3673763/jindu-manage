package com.ucsmy.jindu.manage.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.aop.exception.ServiceException;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.config.UserApiConfig;
import com.ucsmy.jindu.manage.manage.service.ManageCommonService;
import com.ucsmy.jindu.manage.manage.service.ManageHttpAosResultService;
import com.ucsmy.jindu.manage.manage.service.SysCacheService;
import com.ucsmy.jindu.manage.manage.service.SysTokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/***
 * 
 * @author ucs_hexuejun
 *
 */
@Service
public class SysTokenManagerServiceImpl implements SysTokenManagerService {
    protected static String TOKEN_MANAGER_PATH = "jindu:manager:token";
    protected static String TOKEN_KEY = "tokeninfo";
    protected final String systoken_key = "loginfo";
    @Autowired
    private SysCacheService sysCacheService;

    @Autowired
    private UserApiConfig userApiConfig;

    @Autowired
    private ManageHttpAosResultService manageHttpAosResultService;

    @Autowired
    private ManageCommonService manageCommonService;

    @Override
    @Logger(printSQL = true)
    public String getValidToken() throws Exception {
        Map tokenInfo = new HashMap();
        tokenInfo = this.getTokenByCache();
        if (tokenInfo != null) {

            return tokenInfo.get("token").toString();
        } else {
            tokenInfo = this.getTokenInfo();
            this.saveTokenInfo(tokenInfo, Long.valueOf(tokenInfo.get("time").toString()));
        }
        return tokenInfo.get("token").toString();
    }

    @Override
    @Logger(printSQL = true)
    public String getValidToken(String status) throws Exception {
        Map tokenInfo = new HashMap();
        tokenInfo = this.getTokenByCache();
        if (tokenInfo != null) {
            if (status != null) {
                this.cleanTokenCache();
            } else {

                return tokenInfo.get("token").toString();
            }
        }
        tokenInfo = this.getTokenInfo();
        this.saveTokenInfo(tokenInfo, Long.valueOf(tokenInfo.get("time").toString()));
        return tokenInfo.get("token").toString();
    }

    @Override
    @Logger(printSQL = true)
    public void saveTokenInfo(Map token, Long time) {
        sysCacheService.set(TOKEN_MANAGER_PATH.concat(TOKEN_KEY), token, time);
    }

    @Override
    @Logger(printSQL = true)
    public Map getTokenInfo() throws Exception {
        Map tokenInfo = new HashMap();

        String tokenUrl =  manageCommonService.concantRootUrl(userApiConfig.getToken());
        AosResult aosResult = manageHttpAosResultService.sendGet(tokenUrl);

        if ("0".equals(aosResult.getRetcode())) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(aosResult.getData());
            if (null != jsonObject.get("access_token") && null != jsonObject.get("expires_in")) {
                tokenInfo.put("token", jsonObject.get("access_token"));
                tokenInfo.put("time", jsonObject.get("expires_in"));
            }
        } else {
            throw new ServiceException("调用外部接口异常");
        }
        return tokenInfo;
    }

    @Override
    @Logger(printSQL = true)
    public Map getTokenByCache() {
        return (Map) sysCacheService.get(TOKEN_MANAGER_PATH.concat(TOKEN_KEY));
    }

    @Override
    @Logger(printSQL = true)
    public void cleanTokenCache() {
        sysCacheService.delete(TOKEN_MANAGER_PATH.concat(TOKEN_KEY));
    }

    @Override
    @Logger(printSQL = true)
    public String setSysLoginToken(Map infoMap) {
        sysCacheService.set(TOKEN_MANAGER_PATH.concat(systoken_key), infoMap, Long.valueOf(infoMap.get("expiresIn").toString()));
        return infoMap.get("accessToken").toString();
    }

    @Override
    @Logger(printSQL = true)
    public String getSysLoginToken(String key) {
        String returnValue = "";
        Map map = (Map) sysCacheService.get(TOKEN_MANAGER_PATH.concat(systoken_key));
        if (map != null) {
            returnValue = map.get(key).toString();
        }
        return returnValue;
    }

}
