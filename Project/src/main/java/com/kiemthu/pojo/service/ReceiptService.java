/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Receipt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class ReceiptService {

    public List<Receipt> getReceipt() throws SQLException{
        Connection conn = JdbcUtils.getconn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM receipt");
        
        List<Receipt> re =  new ArrayList<>();
        while(rs.next()){
            Receipt r  = new Receipt();
            r.setId(rs.getInt("idreceipt"));
            r.setTotal(rs.getFloat("total"));
            r.setCreateDate(rs.getDate("created_date"));
            r.setStaff_id(rs.getInt("staff_id"));
            r.setCustomer_id(rs.getInt("customer_id"));
            
            re.add(r);
        }
        
        conn.close();
        return re;
    }
    //add a Receipt, id tang tu dong.
    // feature : ngay tao tư dong - and co the sua khi can.
    public boolean addReceipt(Receipt r) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        String sql = "iNSERT INTO receipt(created_date,total,customer_id,staff_id) VALUES(?,?,?,?)"  ;
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setDate(1, r.getCreateDate());
        stm.setFloat(2, r.getTotal());
        stm.setInt(3, r.getCustomer_id());
        stm.setInt(4, r.getStaff_id());
        
        int row = stm.executeUpdate();
        
       return row > 0;
    }
    
    public List<Receipt> SearchReceiptById(int kw) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        String sql = "SELECT * FROM receipt WHERE id = ?" ;
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<Receipt> re = new ArrayList<>();
        while(rs.next()){
            Receipt r = new Receipt();
            r.setId(rs.getInt("idreceipt"));
            r.setTotal(rs.getFloat("total"));
            r.setCreateDate(rs.getDate("created_date"));
            r.setStaff_id(rs.getInt("staff_id"));
            r.setCustomer_id(rs.getInt("customer_id"));
            
            re.add(r);
            System.out.printf("\n%d\t\t\t\t",
                                  r.getId(),r.getCreateDate(),r.getCustomer_id(),r.getStaff_id(),r.getTotal());
            
        }
        
        return re;
    }
}
