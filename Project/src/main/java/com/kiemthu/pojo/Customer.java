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
public class Customer extends User{

    public Customer( String name, String email, String username, String password, String avatar, Boolean gender, Date birthDate, Date createDate, String phone, String address, Role userRole) {
        super(name, email, username, password, avatar, gender, birthDate, createDate, phone, address, userRole);
    }

}
