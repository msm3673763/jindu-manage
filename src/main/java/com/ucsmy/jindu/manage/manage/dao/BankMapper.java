package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.Bank;
import com.ucsmy.jindu.manage.manage.ext.BankDetailPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ucs_mojiazhou on 2017/6/13.
 */
@Mapper
public interface BankMapper {

    PageResult<BankDetailPojo> query(@Param("bank")BankDetailPojo bankDetailPojo, PageRequest pageRequest);

    int add(Bank bank);

    int update(Bank bank);

    int del(String id);

    BankDetailPojo queryDetail(String id);

    int isExitNo(@Param("bankNo")String bankNo,@Param("id")String id);

}
