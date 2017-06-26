package com.ucsmy.jindu.manage.manage.service;

import java.util.List;
import java.util.Map;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.ManageOrganization;
import com.ucsmy.jindu.manage.manage.ext.UcasClientOrganizationUser;
import com.ucsmy.jindu.manage.manage.ext.UcasClientProfileOrganization;

/**
 * Created by ucs_panwenbo on 2017/4/14.
 * @author ucs_panwenbo on 2017/4/14.
 */
public interface ManageOrganizationService {

     List<ManageOrganization> getOrganizationList();

     ManageOrganization getOrganizationById(String id);

     int updateOrganization(ManageOrganization manageOrganization);

     int insertOrganization(ManageOrganization manageOrganization);

     int deleteOrganization(String id);

     int countChildren(String id);

     int deleteBatchByUserIds(Map<String,Object> map);

     int deleteBatch(Map<String,Object> map);

     int insertBatch( List<UcasClientOrganizationUser> list);

     PageResult<UcasClientProfileOrganization> queryUserWithOrganization(String id, int page, int size);

     PageResult<UcasClientProfileOrganization> queryUserWithoutOrganization(String id, int page, int size);

}
