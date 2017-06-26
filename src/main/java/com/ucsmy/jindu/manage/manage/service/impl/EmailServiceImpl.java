package com.ucsmy.jindu.manage.manage.service.impl;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDUtils;
import com.ucsmy.jindu.manage.manage.dao.EmailMapper;
import com.ucsmy.jindu.manage.manage.entity.Email;
import com.ucsmy.jindu.manage.manage.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ucs_mojiazhou on 2017/6/13.
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailMapper emailMapper;


    @Override
    public PageResult<Email> query(String name, int page, int size) {
        return emailMapper.query(name,new PageRequest(page,size));
    }

    @Override
    public AosResult addJinDuEmail(Email email) {
        if(this.isExitCom(email.getDomainName(),email.getUuid()))
            return AosResult.retFailureMsg("已存在该域名");
        email.setUuid(UUIDUtils.getUUID32());
       int i = emailMapper.addJinDuEmail(email);
        if(i>0)
            return AosResult.retSuccessMsg("添加成功");
        else
           return AosResult.retFailureMsg("添加失败");
    }

    @Override
    public AosResult updateJinDuEmail(Email email) {

        if(null==this.queryDetail(email.getUuid()))
        {
            return AosResult.retFailureMsg("此数据已被删除，不能修改");
        }
        if(this.isExitCom(email.getDomainName(),email.getUuid()))
            return AosResult.retFailureMsg("已存在该域名");
        int i = emailMapper.updateJinDuEmail(email);

        if(i>0)
            return AosResult.retSuccessMsg("更新成功");
        else
            return AosResult.retFailureMsg("更新失败");
    }

    @Override
    public boolean isExitCom(String domainName, String uuid) {
        return emailMapper.isExitCom(domainName,uuid)>0;
    }

    @Override
    public AosResult delJinDuEmail(String uuid) {
        int i = emailMapper.delJinDuEmail(uuid);
        if(i>0)
            return AosResult.retSuccessMsg("删除成功");
        else
            return AosResult.retFailureMsg("删除失败");
    }

    @Override
    public Email queryDetail(String uuid) {
        return emailMapper.queryDetail(uuid);
    }
}
