package com.ucsmy.jindu.manage.manage.service.impl;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/19

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.dao.BankInfoMapper;
import com.ucsmy.jindu.manage.manage.entity.BankInfo;
import com.ucsmy.jindu.manage.manage.service.BankInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/19
 */
@Service
public class BankInfoServiceImpl implements BankInfoService{


    @Autowired
    private BankInfoMapper bankInfoMapper;

    private static final String USING_STATUS = "1";
    private static final String UN_USING_STATUS = "2";

    @Override
    public PageResult<BankInfo> query(BankInfo bankInfo, int page, int size) {
        return bankInfoMapper.query(bankInfo,new PageRequest(page,size));
    }

    @Override
    public AosResult using(BankInfo bankInfo) {
        BankInfo nbankInfo1 = bankInfoMapper.queryByCode(bankInfo.getCode());
        if(null==nbankInfo1)
        {
            return AosResult.retFailureMsg("数据不存在，不能修改");
        }
        if(nbankInfo1.getStatus().equals(USING_STATUS))
        {
            return AosResult.retFailureMsg("此银行状态已为启用，不用启用");
        }
        bankInfo.setStatus(USING_STATUS);
        int i = bankInfoMapper.updateStatus(bankInfo);
        if(i>0)
            return AosResult.retSuccessMsg("启用成功");
        return AosResult.retFailureMsg("禁用失败");
    }

    @Override
    public AosResult unUsing(BankInfo bankInfo) {
        BankInfo nbankInfo1 = bankInfoMapper.queryByCode(bankInfo.getCode());
        if(null==nbankInfo1)
        {
            return AosResult.retFailureMsg("数据不存在，不能修改");
        }
        if(nbankInfo1.getStatus().equals(UN_USING_STATUS))
        {
            return AosResult.retFailureMsg("此银行状态已为禁用，不用禁用启用");
        }
        bankInfo.setStatus(UN_USING_STATUS);
        int i = bankInfoMapper.updateStatus(bankInfo);
        if(i>0)
            return AosResult.retSuccessMsg("禁用成功");
        return AosResult.retFailureMsg("禁用失败");
    }

}