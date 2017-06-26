package com.ucsmy.jindu.manage.config.oauth2authorize.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.jindu.manage.commons.http.service.SysHttpRequestService;
import com.ucsmy.jindu.manage.config.EnterpriseAccountConfig;
import com.ucsmy.jindu.manage.config.LoginTypeConfig;
import com.ucsmy.jindu.manage.config.oauth2authorize.Oauth2Http;
import com.ucsmy.jindu.manage.config.shiro.ShiroUtils;
import com.ucsmy.jindu.manage.manage.service.ManageCommonService;
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
public class Oauth2Httpimpl implements Oauth2Http {
	protected final String scope = "client_userinfo";
	protected final String granttype = "authorization_code";
	protected final int countreturn = 5;
    static  final String  INFO_URL = "http://client:secret@localhost/oauth/info";

	@Autowired
	protected SysTokenManagerService sysTokenManagerService;
	@Autowired
	protected SysHttpRequestService httpRequestService;
	@Autowired
	private LoginTypeConfig loginTypeConfig;

	@Autowired
	private ManageCommonService manageCommonService;

	@Autowired
	private EnterpriseAccountConfig enterpriseAccountConfig;

	@Override
	public String getAccessToken(String accessCod) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grant_type", granttype);
		map.put("scope", scope);
		//map.put("code", "ae9868f9a0552549f0");
		map.put("code", accessCod);
		map.put("client_id", enterpriseAccountConfig.getClientId());
		map.put("client_secret", enterpriseAccountConfig.getClientSecret());
		String msg = httpRequestService.sendHttpGetMap(manageCommonService.concantRootUrl(loginTypeConfig.getGetTokenUrl()), map);
		String accessToken = "";
		JSONObject jasonObject = JSONObject.parseObject(msg);
		Map mapResp = new HashMap();
		if (jasonObject.size() ==countreturn) {
			accessToken = jasonObject.get("access_token").toString();
			String openId = jasonObject.get("openid").toString();
			String expiresIn = jasonObject.get("expires_in").toString();
			String refreshToken = jasonObject.get("refresh_token").toString();
			mapResp.put("openId", openId);
			mapResp.put("accessToken", accessToken);
			mapResp.put("refreshToken", refreshToken);
			mapResp.put("expiresIn", expiresIn);
			accessToken = sysTokenManagerService.setSysLoginToken(mapResp);
		}
		return accessToken;
	}
	
	@Override
	public boolean checkAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUsernameByAccessToken(String accessToken)  {
		// TODO Auto-generated method stub
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("access_token", accessToken);
		try {
			msg = httpRequestService.sendHttpGetMap(INFO_URL, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject  jasonObject = JSONObject.parseObject(msg);
		String userName  =jasonObject.get("userName").toString();
	return userName;
	}

	@Override
	public long getExpireIn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean userLogin(String token) {
		// TODO Auto-generated method stub

		Boolean  bo = ShiroUtils.autoLogin(token);
 
		return bo;
	}
}
