package com.ucsmy.jindu.manage.manage.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/16

 * Contributors:
 *      - initial implementation
 */

import java.io.Serializable;

/**
 * 省
 *
 * @author ucs_mojiazhou
 * @since 2017/6/16
 */

public class Province implements Serializable{

    private String id;
    private String provinceNo;
    private String provinceName;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}