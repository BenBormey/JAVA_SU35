/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atm.transaction.system.model;

/**
 *
 * @author HQ
 */
public class User {
    private int userId ;
    private String username ;
    private String password;
    private int roleId;
    private boolean active;
    public User(){
    }
    public User(int UserId_, String UserName_, String Password_, int roleId_, boolean active_)
    {
        this.userId = UserId_;
        this.username = UserName_;
        this.password = Password_;
        this.roleId = roleId_;
        this.active = active_;
    
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }

    public boolean isActive() {
        return active;
    }
    

    
}
