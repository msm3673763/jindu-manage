package com.ucsmy.jindu.manage.manage.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ucs_zhengfucheng on 2017/6/13.
 */
public class Client implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * md5
     */
    private String md5;
    /**
     * 描述
     */
    private String description;
    /**
     * 文件在mongodb中的id
     */
    private String fileId;
    /**
     * 创建日期
     */
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", md5='" + md5 + '\'' +
                ", description='" + description + '\'' +
                ", fileId='" + fileId + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
