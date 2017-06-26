package com.ucsmy.jindu.manage.manage.service.impl;

import com.ucsmy.jindu.manage.manage.dao.ManageLogInfoMapper;
import com.ucsmy.jindu.manage.manage.entity.ManageLogInfo;
import com.ucsmy.jindu.manage.manage.service.ManageLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 
 * @author chenqilin on 2017/5/8.
 */
@Service
public class ManageLogInfoServiceImpl implements ManageLogInfoService {

    @Autowired
    private ManageLogInfoMapper manageLogInfoMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int addManageLogInfo(ManageLogInfo manageLogInfo) {
        return manageLogInfoMapper.addManageLogInfo(manageLogInfo);
    }
}
