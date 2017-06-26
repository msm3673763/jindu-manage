package com.ucsmy.jindu.manage.manage.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/19

 * Contributors:
 *      - initial implementation
 */

/**
 * 银行详情
 *
 * @author ucs_mojiazhou
 * @since 2017/6/19
 */

public class BankInfo {

    private  String code;

    private String name;

    private String abbreviation;

    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}