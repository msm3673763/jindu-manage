package com.ucsmy.jindu.manage.manage.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/16

 * Contributors:
 *      - initial implementation
 */

import java.io.Serializable;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/16
 */

public class City implements Serializable{

    private String id;
    private String provinceNo;
    private String cityNo;
    private String cityName;
    private static final long serialVersionUID = 1L;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}