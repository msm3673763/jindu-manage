package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Bank;
import com.ucsmy.jindu.manage.manage.ext.BankDetailPojo;

/**
 * Created by ucs_mojiazhou on 2017/6/13.
 */
public interface BankService {

    //支行列表
    PageResult<BankDetailPojo> query(BankDetailPojo bankDetailPojo, int page, int size);

    AosResult add(Bank bank);

    AosResult update(Bank bank);

    boolean isExitCom(String bankNo,String id);

    AosResult delete(String id);

    BankDetailPojo queryDetail(String id);
}
