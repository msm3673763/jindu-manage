package com.ucsmy.jindu.manage.manage.service;

import java.util.Map;

/**
 * Created by ucs_mojiazhou on 2017/6/20.
 */
public interface SysTicketService {

    /**
     * 获取ticket
     * @return
     */
    String getTicket() throws Exception;


   Map getTicketInfo() throws Exception;

}
