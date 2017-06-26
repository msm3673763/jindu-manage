package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.Email;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ucs_mojiazhou on 2017/6/13.
 */
@Mapper
public interface EmailMapper {

    PageResult<Email> query(@Param("name") String  name, PageRequest pageRequest);

    int addJinDuEmail(Email email);

    int updateJinDuEmail(Email email);

    int delJinDuEmail(String uuid);

    Email queryDetail(String uuid);

    int isExitCom(@Param("domainName")String domainName,@Param("uuid") String uuid);
}
