package com.ucsmy.jindu.manage.manage.dao;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/19

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.BankInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/19
 */
@Mapper
public interface BankInfoMapper {

    PageResult<BankInfo> query(@Param("bankInfo") BankInfo bankInfo, PageRequest pageRequest);

    BankInfo queryByCode(String code);

    int updateStatus(BankInfo bankInfo);

}