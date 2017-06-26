package com.ucsmy.jindu.manage.manage.entity;

import java.util.Date;

/**
 * Created by ucs_zhengfucheng on 2017/6/15.
 */
public class Feedback {

    /**
     * 主键
     */
    private String id;

    /**
     * 反馈信息
     */
    private String message;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 工单号
     */
    private String orderNumber;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 处理用户
     */
    private String processUser;

    /**
     * 处理时间
     */
    private Date processDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProcessUser() {
        return processUser;
    }

    public void setProcessUser(String processUser) {
        this.processUser = processUser;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
}
