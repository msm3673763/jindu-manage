package com.ucsmy.jindu.manage.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.constant.IntegrationCode;
import com.ucsmy.jindu.manage.commons.constant.IntegrationCode;
import com.ucsmy.jindu.manage.commons.http.service.SysHttpRequestService;
import com.ucsmy.jindu.manage.manage.service.ManageHttpAosResultService;
import com.ucsmy.jindu.manage.manage.service.SysTicketService;
import com.ucsmy.jindu.manage.manage.service.SysTokenManagerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by
 * 
 * @author ucs_mojiazhou on 2017/4/28.
 */
@Service
public class ManageHttpAosResultServerImpl implements ManageHttpAosResultService {

	private static final String INVALID_TOKEN = "invalid_token";

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ManageHttpAosResultServerImpl.class);

	@Autowired
	private SysTicketService sysTicketService;

	@Autowired
	private SysHttpRequestService sysHttpRequestService;

	@Override
	@Logger(operationName = "发送postJson", printSQL = true)
	public AosResult sendPostJson(String url, String strJson) {

		/**ticket失效**/
		AosResult aosResult = this.firsendPostJson(url, strJson);
		if (aosResult.getRetmsg().equals(IntegrationCode.INVAIL_TICKET.getName())) {
			Map ticketInfo = null;
			try {
				ticketInfo = sysTicketService.getTicketInfo();
			} catch (Exception e) {
				LOG.error(e.getMessage());
				return AosResult.retFailureMsg("调用外部接口异常");
			}
			Map map = JSONObject.parseObject(strJson, Map.class);
			map.put("ticket", ticketInfo.get("call_ticket"));
			String newStrJson = JSONObject.toJSONString(map);
			return this.firsendPostJson(url, newStrJson);
		}
		return aosResult;
	}

	@Override
	@Logger(operationName = "发送Get", printSQL = true)
	public AosResult sendGet(String url) {
		String entity = null;
		try {
			entity = sysHttpRequestService.sendHttpGet(url);

		} catch (IOException e) {
			LOG.error(e.getMessage());
			return AosResult.retFailureMsg("调用外部接口异常");
		}

		return this.handleResponse(entity);
	}

	@Logger(printSQL = true)
	private AosResult handleResponse(String entity) {
		if (null != entity) {
			JSONObject json = JSONObject.parseObject(entity);
			if (null != json.get("res") && 1!=(int)json.get("res") ) {
				int errcode = (Integer) json.get("res");
				if (errcode == IntegrationCode.FAILURE.getIndex()) {
					return AosResult.retFailureMsg((String) json.get("des"));
				} else if (errcode == IntegrationCode.PARAMETER_ERROR.getIndex()) {
					return AosResult.retFailureMsg((String) json.get("des"));
				} else if (errcode == IntegrationCode.OTHER_ERROR.getIndex() //
						) {
					return AosResult.retFailureMsg((String) json.get("des"));

				} else if (errcode == IntegrationCode.NULL_TICKET.getIndex()) {
					return AosResult.retFailureMsg(IntegrationCode.NULL_TICKET.getName());
				} else if (errcode == IntegrationCode.CREATE_FAILER.getIndex()) {
					return AosResult.retFailureMsg(IntegrationCode.CREATE_FAILER.getName());
				}
				else if (errcode == IntegrationCode.QUERY_NULL.getIndex()) {
					return AosResult.retFailureMsg(IntegrationCode.QUERY_NULL.getName());
				}
				else {
					return AosResult.retFailureMsg(IntegrationCode.INVAIL_TICKET.getName());
				}
			} else {
				return AosResult.retSuccessMsg("success", json);
			}
		} else {
			return AosResult.retFailureMsg("调用外部接口异常");
		}
	}

	@Logger(printSQL = true)
	private AosResult firsendPostJson(String url, String strJson) {

		String entiy = null;
		try {
			entiy = sysHttpRequestService.sendHttpPostJson(url, strJson);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			return AosResult.retFailureMsg("调用外部接口异常");
		}
		return this.handleResponse(entiy);

	}

}
