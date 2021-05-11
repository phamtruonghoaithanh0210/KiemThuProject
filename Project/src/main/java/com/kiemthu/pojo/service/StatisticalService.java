/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.User;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class StatisticalService {
    
    
    
    public StatisticalService(){
    }
    
    
//    public StatisticalService(Connection conn){
//        this.conn = conn;
//    }
    
    public List<User> birthdayStaff (int month) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
  //      int month = date.getMonth()+1;
        CallableStatement cal = conn.prepareCall("{CALL birthday_staff(?)}");
        cal.setInt(1, month);
        List<User> customers = new ArrayList<>();
        ResultSet rs = cal.executeQuery();
        while (rs.next()){
                User c = new User();
                c.setIduser(rs.getInt("iduser"));
                c.setName(rs.getString("name"));
                c.setBirthday(rs.getDate("birthday"));
               
                customers.add(c);
        }
        
        return customers;
    }
    
    public List<User> birthdayCustomer (int month) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
      //  int month = date.getMonth()+1;
        CallableStatement cal = conn.prepareCall("{CALL birthday_customer(?)}");
        cal.setInt(1, month);
        List<User> staffs = new ArrayList<>();
        ResultSet rs = cal.executeQuery();
        while (rs.next()){
                 User c = new User();
                c.setIduser(rs.getInt("iduser"));
                c.setName(rs.getString("name"));
                c.setBirthday(rs.getDate("birthday"));
               
                staffs.add(c);
        }
        return staffs;
    }
    

    public int totalPriceInMonth(int month, int year) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        int t = 0;
        CallableStatement cal = conn.prepareCall("{CALL total_price_of_month_in_years(?,?)}");
        cal.setInt(1, month);
        cal.setInt(2, year);
        
        ResultSet rs = cal.executeQuery();
        while (rs.next()){
                 
                int m = rs.getInt("thang");
                int y = rs.getInt("nam");
                t = rs.getInt("tongtien");
        }
        return t;
    }
//    public static void main (String args []) throws SQLException{
//        StatisticalService s = new StatisticalService();
//        System.out.printf("%d",s.totalPriceInMonth(10, 2021));
//        
//        
//    }
}
