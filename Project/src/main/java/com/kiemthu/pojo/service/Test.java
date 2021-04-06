/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.service.JdbcUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class Test {
     public static void main(String[] args) throws ClassNotFoundException, SQLException{
//        Product p = new Product(4,"Lenovo2020",new BigDecimal(18),1);
        ProductService p1 = new ProductService(JdbcUtils.getConn());
        
        p1.searchByPrice(new BigDecimal(12),new BigDecimal(18));
        
     }
    
}
