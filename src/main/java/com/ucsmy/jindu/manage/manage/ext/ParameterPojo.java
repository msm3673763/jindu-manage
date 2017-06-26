package com.ucsmy.jindu.manage.manage.ext;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/19

 * Contributors:
 *      - initial implementation
 */

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/19
 */

public class ParameterPojo {

    /**
     * id
     */
    private String id;

    /**
     * 参数类别
     */
    private String type;

    /**
     * 参数key
     */
    private String key;

    /**
     * 参数值
     */
    private String value;


    /**
     * 优先级
     */

    private Integer priority;

    private String paramName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }


}