package com.digger.qqcommon;

import jdk.internal.org.objectweb.asm.commons.SerialVersionUIDAdder;

import java.io.Serializable;

/**
 * @Description : 表示一个用户信息
 * @Author : 孙梦琼
 * @Date : 2021/9/12 21:48
 * @Version : 1.0
 **/
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String userName;
    private String password;

    public User(String userId, String pwd){
        this.id = userId;
        this.password = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
