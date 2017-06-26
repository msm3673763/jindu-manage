package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.BankInfo;
/**
 * Created by ucs_mojiazhou on 2017/6/19.
 */
public interface BankInfoService {

    /**
     * 查找银行列表
     * @param bankInfo
     * @param page
     * @param size
     * @return
     */
    PageResult<BankInfo> query(BankInfo bankInfo, int page, int size);

    /**
     * 启用
     * @param bankInfo
     * @return
     */
    AosResult using(BankInfo bankInfo);

    /**
     * 禁用
     * @param bankInfo
     * @return
     */
    AosResult unUsing(BankInfo bankInfo);
}
