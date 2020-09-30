package com.example.fouthapp.entity;


import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import org.litepal.crud.LitePalSupport;

@SmartTable(name = "用户信息列表")
public class User extends LitePalSupport {
    @SmartColumn(id = 1, name = "学工号")
    private String userId;
    @SmartColumn(id = 3,name = "密码")
    private String password;
    @SmartColumn(id = 2, name = "用户名")
    private String username;
    @SmartColumn(id = 4, name = "身份")
    private String role;

    public User(String userId, String password, String username, String role) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
