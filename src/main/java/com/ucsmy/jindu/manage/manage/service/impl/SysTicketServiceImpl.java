package com.ucsmy.jindu.manage.manage.service.impl;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/20

 * Contributors:
 *      - initial implementation
 */

import com.alibaba.fastjson.JSONObject;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.aop.exception.ServiceException;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.config.UserApiConfig;
import com.ucsmy.jindu.manage.manage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/20
 */
@Service
public class SysTicketServiceImpl implements SysTicketService{

    private static final String JINDU_INTEGRATION = "jindu:manage:ticket:";
    private static final String TICKET_KEY = "ticketinfo";

    @Autowired
    private SysCacheService sysCacheService;

    @Autowired
    private SysTokenManagerService sysTokenManagerService;

    @Autowired
    private ManageCommonService manageCommonService;

    @Autowired
    private UserApiConfig userApiConfig;

    @Autowired
    private ManageHttpAosResultService manageHttpAosResultService;

    @Override
    public String getTicket() throws Exception {

        Map ticketInfo = new HashMap();
        ticketInfo = this.getTicketByCache();
        if(null!=ticketInfo)
        {
            return ticketInfo.get("call_ticket").toString();
        }
       else
        {
            ticketInfo = this.getTicketInfo();
            this.saveTicketInfo( JSONObject.toJSONString(ticketInfo),Long.valueOf(ticketInfo.get("expires_in").toString()));
        }
        return ticketInfo.get("call_ticket").toString();
    }

    private void saveTicketInfo(String jsonTicket, Long time) {
        sysCacheService.set(JINDU_INTEGRATION.concat(TICKET_KEY), jsonTicket, time);
    }

    @Override
    @Logger(printSQL = true)
    public Map getTicketInfo() throws Exception {
        Map  tokenInfo = sysTokenManagerService.getTokenInfo();
        String ticketUrl = userApiConfig.getTicket()+tokenInfo.get("token").toString();
         ticketUrl =  manageCommonService.concantRootUrl(ticketUrl);
        AosResult aosResult = manageHttpAosResultService.sendGet(ticketUrl);
       Map ticketInfo = new HashMap<>();
        if ("0".equals(aosResult.getRetcode())) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(aosResult.getData());
            if (null != jsonObject.get("call_ticket") && null != jsonObject.get("expires_in")) {
                ticketInfo.put("call_ticket", jsonObject.get("call_ticket"));
                ticketInfo.put("expires_in", jsonObject.get("expires_in"));
            }
        } else {
            throw new ServiceException("调用外部接口异常");
        }
        return ticketInfo;
    }

    private Map getTicketByCache()
    {
       String jsonTicket =  sysCacheService.getString(JINDU_INTEGRATION.concat(TICKET_KEY));
        if (null!=jsonTicket)
        {
           return JSONObject.parseObject(jsonTicket,Map.class);
        }
        return null;
    }
}