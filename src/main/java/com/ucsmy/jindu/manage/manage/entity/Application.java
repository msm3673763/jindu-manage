package com.ucsmy.jindu.manage.manage.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/15

 * Contributors:
 *      - initial implementation
 */

/**
 * 桌面应用管理
 *
 * @author ucs_mojiazhou
 * @since 2017/6/15
 */

public class Application {
    private String uuid;
    private String applyName;
    private String applyDes;
    private String applyUrl;
    private String imgEnter;
    private String imgMove;
    private String status;
    private String createTime;
    private String modifyTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplyDes() {
        return applyDes;
    }

    public void setApplyDes(String applyDes) {
        this.applyDes = applyDes;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }

    public String getImgEnter() {
        return imgEnter;
    }

    public void setImgEnter(String imgEnter) {
        this.imgEnter = imgEnter;
    }

    public String getImgMove() {
        return imgMove;
    }

    public void setImgMove(String imgMove) {
        this.imgMove = imgMove;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}