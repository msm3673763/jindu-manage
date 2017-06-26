package com.ucsmy.jindu.manage.manage.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author  admin
 */
public class ManageRole implements Serializable {
    /**
     * 角色唯一标识
     */
    private String roleId;

    /**
     * 描述
     */
    private String description;

    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 类型
     */
    @NotEmpty(message = "类型不能为空")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private static final long serialVersionUID = 1L;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String typeDescr;

    public void setTypeDescr(String typeDescr) {
        this.typeDescr = typeDescr;
    }

    public String getTypeDescr() {
        if (StringUtils.hasText(getType())) {
            return getType().equals("0") ? "后台角色" : "金度角色";
        }
        return "";
    }
}
