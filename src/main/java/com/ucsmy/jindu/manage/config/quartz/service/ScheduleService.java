package com.ucsmy.jindu.manage.config.quartz.service;

import org.quartz.Scheduler;

/**
 * quartz service
 * Created by 
 * @author  chenqilin on 2017/4/19.
 */
public interface ScheduleService {
    /**
     * 执行定时调用
     */
    void proceedSchedule();

    void setScheduler(Scheduler scheduler);

}
