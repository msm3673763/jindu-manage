package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.ext.DataPageResult;

/**
 * Created by ucs_zhengfucheng on 2017/6/20.
 */
public interface EnterpriseAccountService {


    DataPageResult listEnterpriseAccount(int pageNum, int pageSize, String status, String startDate, String endDate, String depositor);

    AosResult countCompany();

    AosResult updateCorporDepositNoStatus(String recordId);

}
