/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class Staff extends User{

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    private String username;
    private String password;
    public Staff( String name, String email, String username, String password, String avatar, Boolean gender, Date birthDate, Date createDate, String phone, String address, Role userRole) {
        super( name, email, avatar, gender, birthDate, createDate, phone, address, userRole);
        this.username = username;
        this.password = password;
    }

}
