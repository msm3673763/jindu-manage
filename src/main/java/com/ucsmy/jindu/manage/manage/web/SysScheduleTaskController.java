package com.ucsmy.jindu.manage.manage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.ResultConst;
import com.ucsmy.jindu.manage.commons.base.utils.StringUtils;
import com.ucsmy.jindu.manage.manage.entity.ManageIpScheduleTask;
import com.ucsmy.jindu.manage.manage.service.SysScheduleTaskService;


/**
 * 定时任务模块控制器
 * Created by 
 * @author  chenqilin on 2017/4/18.
 */
@RestController
@RequestMapping("schedule")
public class SysScheduleTaskController {

    private final String task_code_duplicate = "任务码不能重复";
    private final String add_fail = "添加失败，请检查网络";
    private final String add_success = "添加成功";

    private final String update_success = "更新成功";

    private final String task_id_empty = "任务id不能为空";
    private final String task_del_empty = "定时任务不存在，删除失败";
    private final String task_del_fail = "删除失败，请检查网络";
    private final String task_del_success = "删除成功";

    private final String task_star_success = "启动定时任务成功";
    private final String task_stop_success = "关闭定时任务成功";

    @Autowired
    private SysScheduleTaskService sysScheduleTaskService;

    /**
     * 查询定时任务列表
     * @param taskName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list")
    public PageResult<ManageIpScheduleTask> queryScheduleTaskList(@RequestParam(required = false) String taskName, int pageNum, int pageSize) {
        return sysScheduleTaskService.queryScheduleTask(taskName, pageNum, pageSize);
    }

    /**
     * 添加定时任务
     * @param scheduleTask
     * @return
     */
    @RequestMapping("add")
    public AosResult addScheduleTask(ManageIpScheduleTask scheduleTask) {
        AosResult validate = sysScheduleTaskService.validateScheduleTask(scheduleTask);
        if (!String.valueOf(ResultConst.SUCCESS).equals(validate.getRetcode())) {
            return validate;
        }
        if (sysScheduleTaskService.isTaskCodeExist(scheduleTask.getTaskCode(), null) > 0) {
            return AosResult.retFailureMsg(task_code_duplicate);
        }
        int resultCode = sysScheduleTaskService.addSchedulTask(scheduleTask);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(add_fail);
        }
        return AosResult.retSuccessMsg(add_success, null);
    }

    /**
     * 更新定时任务
     * @param scheduleTask
     * @return
     */
    @RequestMapping("update")
    public AosResult updateScheduleTask(ManageIpScheduleTask scheduleTask) {
        AosResult validate = sysScheduleTaskService.validateScheduleTask(scheduleTask);
        if (!String.valueOf(ResultConst.SUCCESS).equals(validate.getRetcode())) {
            return validate;
        }
        AosResult result = sysScheduleTaskService.updateScheduleTask(scheduleTask);
        if (!String.valueOf(ResultConst.SUCCESS).equals(result.getRetcode())) {
            return result;
        }
        return AosResult.retSuccessMsg(update_success, null);
    }

    /**
     * 删除定时任务
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public AosResult deleteScheduleTask(String id) {
        if (StringUtils.isEmpty(id)) {
            return AosResult.retFailureMsg(task_id_empty);
        }
        ManageIpScheduleTask oldScheduleTask = sysScheduleTaskService.getScheduleTaskById(id);
        if (oldScheduleTask == null) {
            return AosResult.retFailureMsg(task_del_empty);
        }
        int resultCode = sysScheduleTaskService.deleteSchedulTask(id);
        if (resultCode <= 0) {
            return AosResult.retFailureMsg(task_del_fail);
        }
        return AosResult.retSuccessMsg(task_del_success, null);
    }

    /**
     * 开始定时任务
     * @param id
     * @return
     */
    @RequestMapping("start")
    public AosResult startScheduleTask(String id) {
        AosResult result = sysScheduleTaskService.startScheduleTask(id);
        if (!String.valueOf(ResultConst.SUCCESS).equals(result.getRetcode())) {
            return result;
        }
        return AosResult.retSuccessMsg(task_star_success, null);
    }

    /**
     * 停止定时任务
     * @param id
     * @return
     */
    @RequestMapping("stop")
    public AosResult stopScheduleTask(String id) {
        AosResult result = sysScheduleTaskService.stopScheduleTask(id);
        if (!String.valueOf(ResultConst.SUCCESS).equals(result.getRetcode())) {
            return result;
        }
        return AosResult.retSuccessMsg(task_stop_success, null);
    }

}
