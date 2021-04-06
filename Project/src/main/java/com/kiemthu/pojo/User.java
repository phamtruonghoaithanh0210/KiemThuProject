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
public class User {

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @return the userRole
     */
    public Role getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    /**
     * @return the iduser
     */
    public int getIduser() {
        return iduser;
    }

    /**
     * @param iduser the iduser to set
     */
    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the gender
     */
    public boolean isGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(boolean gender) {
        this.gender = gender;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the creatDate
     */
    public Date getCreatDate() {
        return createDate;
    }

    /**
     * @param creatDate the creatDate to set
     */
    public void setCreatDate(Date creatDate) {
        this.createDate = creatDate;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the userRole
     */

    private int iduser;
    private String name;
    private String email;
    private String avatar;
    private boolean gender;
    private Date birthday;
    private Date createDate;
    private String phone;
    private String address;
    public static enum Role {
        Staff,
        Customer,
    }
    private Role userRole ;
    public User(String name, String email,String avatar,Boolean gender,Date birthDate,Date createDate, String phone,String address , Role userRole ){
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.gender = gender;
        this.birthday = birthDate;
        this.createDate = createDate;
        this.phone = phone;
        this.address = address;
        this.userRole = userRole;
    }
    public User(String name){
        this.name = name;
    }
   
}
