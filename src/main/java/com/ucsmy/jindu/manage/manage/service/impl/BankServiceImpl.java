package com.ucsmy.jindu.manage.manage.service.impl;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.dao.BankMapper;
import com.ucsmy.jindu.manage.manage.entity.Bank;
import com.ucsmy.jindu.manage.manage.ext.BankDetailPojo;
import com.ucsmy.jindu.manage.manage.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ucs_mojiazhou on 2017/6/13.
 */
@Service
public class BankServiceImpl implements BankService{

    @Autowired
    private BankMapper bankMapper;


    @Override
    public PageResult<BankDetailPojo> query(BankDetailPojo bankDetailPojo, int page, int size) {
        PageResult<BankDetailPojo> pageResult = bankMapper.query(bankDetailPojo,new PageRequest(page,size));
        return  pageResult;

    }

    @Override
    public AosResult add(Bank bank) {

        int i = bankMapper.add(bank);
        if(i>0)
        {
            return AosResult.retSuccessMsg("添加成功");
        }
           return AosResult.retFailureMsg("添加失败");
    }

    @Override
    public AosResult update(Bank bank) {


        if(null==this.queryDetail(bank.getId()))
        {
            return AosResult.retFailureMsg("此数据已不存在，不能更新");
        }

        int i = bankMapper.update(bank);
        if(i>0)
        {
            return AosResult.retSuccessMsg("更新成功");
        }
        return AosResult.retFailureMsg("更新失败");
    }

    @Override
    public boolean isExitCom(String bankNo, String id) {
        return bankMapper.isExitNo(bankNo,id)>0;
    }

    @Override
    public AosResult delete(String id) {
        int i = bankMapper.del(id);
        if(i>0)
        {
            return AosResult.retSuccessMsg("删除成功");
        }
        return AosResult.retFailureMsg("删除失败");
    }

    @Override
    public BankDetailPojo queryDetail(String id) {
        return bankMapper.queryDetail(id);
    }
}
