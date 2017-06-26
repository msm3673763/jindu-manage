package com.ucsmy.jindu.manage.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.manage.dao.ManageOrganizationMapper;
import com.ucsmy.jindu.manage.manage.entity.ManageOrganization;
import com.ucsmy.jindu.manage.manage.ext.UcasClientOrganizationUser;
import com.ucsmy.jindu.manage.manage.ext.UcasClientProfileOrganization;
import com.ucsmy.jindu.manage.manage.service.ManageOrganizationService;

/**
 * Created by 
 * @author ucs_panwenbo on 2017/4/14.
 */
@Service
public class ManageOrganizationServiceImpl implements ManageOrganizationService {
    @Autowired
   protected ManageOrganizationMapper manageOrganizationMapper;

    @Override
    @Logger(printSQL = true)
    public List<ManageOrganization> getOrganizationList() {
        return manageOrganizationMapper.getOrganizationList();
    }

    @Override
    @Logger(printSQL = true)
    public ManageOrganization getOrganizationById(String id) {
        return manageOrganizationMapper.getOrganizationById(id);
    }

    @Override
    @Logger(printSQL = true)
    public int updateOrganization(ManageOrganization manageOrganization) {
        return manageOrganizationMapper.updateOrganization(manageOrganization);
    }

    @Override
    @Logger(printSQL = true)
    public int insertOrganization(ManageOrganization manageOrganization) {
        return manageOrganizationMapper.insertOrganization(manageOrganization);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteOrganization(String id) {
        return manageOrganizationMapper.deleteOrganization(id);
    }

    @Override
    @Logger(printSQL = true)
    public int countChildren(String id) {
        return manageOrganizationMapper.countChildren(id);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteBatchByUserIds(Map<String, Object> map) {
        return manageOrganizationMapper.deleteBatchByUserIds(map);
    }

    @Override
    @Logger(printSQL = true)
    public int insertBatch(List<UcasClientOrganizationUser> list) {
        return manageOrganizationMapper.insertBatch(list);
    }

    @Override
    @Logger(printSQL = true)
    public int deleteBatch(Map<String, Object> map) {
        return manageOrganizationMapper.deleteBatch(map);
    }

    @Override
    @Logger(printSQL = true)
    public PageResult<UcasClientProfileOrganization> queryUserWithOrganization(String id, int page, int size) {
        return manageOrganizationMapper.queryUserWithOrganization(id, new PageRequest(page,size));
    }

    @Override
    @Logger(printSQL = true)
    public PageResult<UcasClientProfileOrganization> queryUserWithoutOrganization(String id, int page, int size) {
        return manageOrganizationMapper.queryUserWithoutOrganization(id, new PageRequest(page,size));
    }
}
