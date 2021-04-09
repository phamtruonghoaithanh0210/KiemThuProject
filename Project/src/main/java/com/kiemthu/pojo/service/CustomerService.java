/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class CustomerService {
    public List<Customer> getCustomer() throws SQLException {
        Connection conn = JdbcUtils.getconn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM user where user_role like 'Customer' ");
        List<Customer> Customers = new ArrayList<>();
        while (rs.next()) {
            // xư li bang nhan vien lay username password
            Customer c = new Customer();
            c.setIduser(rs.getInt("iduser"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setAvatar(rs.getString("avatar"));
            c.setGender(rs.getBoolean("gender"));
            c.setBirthday(rs.getDate("birthday"));
            c.setCreatDate(rs.getDate("create_date"));
            c.setPhone(rs.getString("phone"));
            c.setUserRole(User.Role.Customer);
            Customers.add(c);
        }
        conn.close();
        return Customers;
    }
    //Search staff by id 
    public Customer searchByID(int id) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        String s = "SELECT * FROM user, customer where iduser = idcustomer and iduser = ?;";
        PreparedStatement pre = conn.prepareStatement(s);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        Customer customer = new Customer();
        while (rs.next()){
            customer.setIduser(rs.getInt("iduser"));
            customer.setName(rs.getString("name"));
            customer.setEmail(rs.getString("email"));
            customer.setAvatar(rs.getString("avatar"));
            customer.setGender(rs.getBoolean("gender"));
            customer.setBirthday(rs.getDate("birthday"));
            customer.setAddress(rs.getString("address"));
            customer.setCreatDate(rs.getDate("create_date"));
            customer.setPhone(rs.getString("phone"));
            customer.setUserRole(User.Role.Customer);
        }
        if(customer.getName() == null)
            return null;
        return customer;
    }
}
