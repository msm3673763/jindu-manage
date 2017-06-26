package com.ucsmy.jindu.manage.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.config.EnterpriseAccountConfig;
import com.ucsmy.jindu.manage.manage.ext.DataPageResult;
import com.ucsmy.jindu.manage.manage.service.EnterpriseAccountService;
import com.ucsmy.jindu.manage.manage.service.ManageCommonService;
import com.ucsmy.jindu.manage.manage.service.ManageHttpAosResultService;
import com.ucsmy.jindu.manage.manage.service.SysTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ucs_zhengfucheng on 2017/6/20.
 */
@Service
public class EnterpriseAccountServiceImpl implements EnterpriseAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnterpriseAccountServiceImpl.class);


    @Autowired
    private ManageHttpAosResultService manageHttpAosResultService;

    @Autowired
    private ManageCommonService manageCommonService;

    @Autowired
    private SysTicketService sysTicketService;

    @Autowired
    private EnterpriseAccountConfig enterpriseAccountConfig;

    @Override
    public DataPageResult listEnterpriseAccount(int pageNum, int pageSize, String status, String startDate, String endDate, String depositor) {
        DataPageResult pageResult = new DataPageResult();
        try {
            //1. 获取 ticket
            String ticket = sysTicketService.getTicket();
            Map<String, Object> map = new HashMap<>();
            map.put("ticket", ticket);
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            if(null != depositor && !depositor.equals("")) {
                map.put("depositor", depositor);
            }
            if(null != startDate && !startDate.equals("")) {
                map.put("timeStart", startDate);
            }
            if(null != endDate && !endDate.equals("")) {
                map.put("timeEnd", endDate);
            }
            if(null != startDate && !status.equals("")) {
                map.put("status", Integer.parseInt(status));
            }

            String parameter = JSONObject.toJSONString(map);
            String url = manageCommonService.concantIntegration(enterpriseAccountConfig.getListDetailUrl());
            //2. 调用接口，获取数据
            AosResult result = manageHttpAosResultService.sendPostJson(url, parameter);
            if(result.getRetcode().equals("-1")) {
                pageResult.setPageNo(1);
                pageResult.setPageSize(10);
                pageResult.setTotalCount(0);
                pageResult.setPages(0);
                pageResult.setResultList("");
            } else {
                JSONObject jsonObject = (JSONObject) result.getData();
                JSONObject dataMap = (JSONObject) jsonObject.get("data");
                pageResult.setPageNo(Integer.parseInt(dataMap.get("pageNum").toString()));
                pageResult.setPageSize(Integer.parseInt(dataMap.get("pageSize").toString()));
                pageResult.setTotalCount(Integer.parseInt(dataMap.get("total").toString()));
                pageResult.setPages(Integer.parseInt(dataMap.get("pageSize").toString()));
                pageResult.setResultList(dataMap.get("list") );
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return pageResult;
    }

    @Override
    public AosResult countCompany() {

        try {
            String ticket =  sysTicketService.getTicket();

            Map<String,Object> map = new HashMap<>();
            map.put("ticket",ticket);
            map.put("statisticType",0);
            String strJson = JSONObject.toJSONString(map);
            String url =  manageCommonService.concantIntegration(enterpriseAccountConfig.getCompanyCount());
            AosResult aosResult =   manageHttpAosResultService.sendPostJson(url,strJson);
           if(!"0".equals(aosResult.getRetcode()))
           {
               return AosResult.retFailureMsg("数据");
           }
            Map<String,String> resultMap = new HashMap<>();

            JSONObject jsonObject = (JSONObject) aosResult.getData();
            JSONArray njsonObject = (JSONArray) jsonObject.get("data");
            Iterator<Object> iterator = njsonObject.iterator();
            while(iterator.hasNext()) {
                JSONObject ob = (JSONObject) iterator.next();
                int type = (int) ob.get("statisticType");
                if(type==1)
                    resultMap.put("countCompany",ob.get("statisticSum").toString());
                if(type==2)
                    resultMap.put("countAccountNo",ob.get("statisticSum").toString());
            }
            return AosResult.retSuccessMsg("success",resultMap);
        } catch (Exception e) {
            LOGGER.debug("EnterpriseAccountServiceImpl",e);
        }
        return AosResult.retFailureMsg("失败");
    }

    @Override
    public AosResult updateCorporDepositNoStatus(String recordId) {
        try {
            String ticket =  sysTicketService.getTicket();

            Map map = new HashMap<>();
            map.put("ticket",ticket);
            map.put("recordId",recordId);
            String strJson = JSONObject.toJSONString(map);
            String url =  manageCommonService.concantIntegration(enterpriseAccountConfig.getUpdateCorporDepositNoStatus());
            return   manageHttpAosResultService.sendPostJson(url,strJson);
        } catch (Exception e) {
            LOGGER.debug("EnterpriseAccountServiceImpl",e);
        }
        return AosResult.retFailureMsg("失败");
    }


}
