/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class StatisticalService {
    private Connection conn;
    
    
    public StatisticalService(){
    }
    
    
    public StatisticalService(Connection conn){
        this.conn = conn;
    }
    
    public void birthdayStaff () throws SQLException{
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        int month = date.getMonth()+1;
        CallableStatement cal = this.conn.prepareCall("{CALL birthday_staff(?)}");
        cal.setInt(1, month);
        ResultSet rs = cal.executeQuery();
        while (rs.next()){
                int id = rs.getInt("iduser");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                Date birthday = rs.getDate("birthday");
                System.out.printf("\n%d\t%s\t%s\t%s",id,name,gender,f.format(birthday));
        }
     
    }

 
}