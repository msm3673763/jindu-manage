package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ucs_mojiazhou on 2017/6/15.
 */
@Mapper
public interface ApplicationMapper {


    PageResult<Application> query(@Param("applyName") String applyName, PageRequest pageRequest);

    int add(Application application);

    int update(Application application);

    int del(String uuid);

    Application queryDetail(String uuid);

    int isNameExist(String name);

    int isNameExistWithId(@Param("name") String name, @Param("uuid") String uuid);
}
