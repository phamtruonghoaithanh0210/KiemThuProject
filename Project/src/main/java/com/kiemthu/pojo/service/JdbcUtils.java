/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class JdbcUtils {
        static{
        try{
            Class.forName("com.mysql.cj.jdbbc.Driver");
        } catch (ClassNotFoundException ex) {
           Logger.getLogger(JdbcUtils.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public static Connection getconn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/saleappphone", "root", "123456789");
    }
}
