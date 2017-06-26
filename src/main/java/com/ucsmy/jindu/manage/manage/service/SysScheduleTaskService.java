package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.ManageIpScheduleTask;


/**
 * Created by 
 * @author  chenqilin on 2017/4/18.
 */
public interface SysScheduleTaskService {

	PageResult<ManageIpScheduleTask> queryScheduleTask(String taskName, int page, int size);

    ManageIpScheduleTask getScheduleTaskById(String id);

    /**
     * 检查taskCode是否存在
     * @param taskCode
     * @param id scheduleTask主键id，非必填，如果填了查询时过滤这条id
     * @return
     */
    int isTaskCodeExist(String taskCode, String id);

    int addSchedulTask(ManageIpScheduleTask scheduleTask);

    /**
     * 更新scheduleTask，开启中的任务不能更新
     * @param scheduleTask
     * @return
     */
    AosResult updateScheduleTask(ManageIpScheduleTask scheduleTask);

    AosResult startScheduleTask(String id);

    AosResult stopScheduleTask(String id);

    int deleteSchedulTask(String id);

    /**
     * 验证scheduleTask属性值
     * @param scheduleTask
     * @return
     */
    AosResult validateScheduleTask(ManageIpScheduleTask scheduleTask);
}
