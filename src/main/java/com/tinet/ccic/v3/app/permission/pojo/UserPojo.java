package com.tinet.ccic.v3.app.permission.pojo;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-10 19:56
 * Email:johnny_lx@yahoo.com
 */
public class UserPojo {
    private Integer enterpriseId;
    private String name;
    private String pwd;
    private String description;
    private Date createTime;
    private Integer roleId;
    private Integer isRightOn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsRightOn() {
        return isRightOn;
    }

    public void setIsRightOn(Integer isRightOn) {
        this.isRightOn = isRightOn;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
