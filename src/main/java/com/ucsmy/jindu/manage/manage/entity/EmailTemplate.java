package com.ucsmy.jindu.manage.manage.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author admin
 */
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否已删除
     */
    public static final String DELETED_NO = "0";
    public static final String DELETED_YES = "1";

    /**
     * 模板编号
     */
    private String id;

    /**
     * 模板主题
     */

    private String title;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 模板类别
     */
    private String type;

    /**
     * 模板类别key
     */
    private String typeKey;

    /**
     * 模板类别名
     */
    private String typeName;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否已删除
     */
    private String isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotBlank(message = "模板主题不能为空")
    @Length(max = 100, message = "模板主题输入长度不能超过100")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank(message = "模板内容不能为空")
    @Length(max = 1000, message = "模板内容输入长度不能超过1000")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotBlank(message = "模板类型不能为空")
    @Length(max = 32, message = "模板类型输入有误")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(max = 1000, message = "模板描述输入长度不能超过1000")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
