package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Email;

/**
 * Created by ucs_mojiazhou on 2017/6/13.
 */
public interface EmailService {

    //邮箱域名列表
    PageResult<Email> query(String name, int page, int size);

    AosResult addJinDuEmail(Email email);

    AosResult updateJinDuEmail(Email email);

    boolean isExitCom(String domainName,String uuid);

    AosResult delJinDuEmail(String uuid);

    Email queryDetail(String uuid);



}
