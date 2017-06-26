package com.ucsmy.jindu.manage.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.ManageIpScheduleTask;


/**
 * Created by 
 * @author chenqilin on 2017/4/18.
 */
@Mapper
public interface ManageIpScheduleTaskMapper {

	PageResult<ManageIpScheduleTask> queryScheduleTask(@Param("taskName") String taskName, PageRequest pageRequest);

    ManageIpScheduleTask getScheduleTaskById(@Param("id") String id);

    int isTaskCodeExist(@Param("taskCode") String taskCode, @Param("id") String id);

    int addSchedulTask(ManageIpScheduleTask scheduleTask);

    int updateScheduleTask(ManageIpScheduleTask scheduleTask);

    int deleteSchedulTask(@Param("id") String id);

    List<ManageIpScheduleTask> getAll();
}
