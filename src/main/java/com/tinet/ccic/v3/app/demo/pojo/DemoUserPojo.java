package com.tinet.ccic.v3.app.demo.pojo;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: LuoYao
 * Date: 14-8-6
 * Time: 17:20
 * Email:johnny_lx@yahoo.com
 */
public class DemoUserPojo {

    private int id;
    private String name;
    private String address;
    private Date createTime;

    public DemoUserPojo() {

    }

    public DemoUserPojo(int id, String name, String address, Date createTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
