/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Receipt;
import com.kiemthu.pojo.Receipt_Detail;
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
    public boolean addReceipt(Receipt r ) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        String sql = "INSERT INTO receipt(created_date,customer_id,staff_id) VALUES(?,?,?)"  ;
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setDate(1, r.getCreateDate());
        stm.setInt(2, r.getCustomer_id());
        stm.setInt(3, r.getStaff_id());
        
        int row = stm.executeUpdate();
                
         return row > 0;
    }
    
    
    public boolean addReceipt_Detail(int productid, int quantity) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT idreceipt FROM receipt ORDER BY idreceipt desc LIMIT 1 ");
        rs.absolute(1);
        //id cua Receipt vua tao ra.
        int id = rs.getInt(1);
        String sql = "INSERT INTO receipt_detail(product_id,quantity,receipt_id) VALUES(?,?,?)"  ;
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, productid);
        stm.setInt(2, quantity);
        stm.setInt(3, id);
        stm.executeUpdate();
        
        //lay so luong hien tại.
        int oldQuantity  = 0;
        String s1 = "SELECT quantity FROM product WHERE idproduct = ?";
        PreparedStatement ps = conn.prepareStatement(s1);
        ps.setInt(1, productid);
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            oldQuantity = resultSet.getInt("quantity");
        }
        // update lại số lượng
        int newQuantity = oldQuantity - quantity;
        String s2 = "UPDATE product SET quantity = ? WHERE idproduct = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(s2);
        preparedStatement.setInt(1, newQuantity);
        preparedStatement.setInt(2, productid);
        int row = preparedStatement.executeUpdate();
        
        conn.close();
        return row >0 ;
        
    }
    
    
    public boolean uppdateReceiptToTal() throws SQLException{
        float total = 0;
        Connection conn = JdbcUtils.getconn();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT idreceipt FROM receipt ORDER BY idreceipt desc LIMIT 1 ");
        rs.absolute(1);
        int id = rs.getInt(1);
        String sql = "SELECT SUM(d.quantity * p.price) as SoLuong \n" + 
                     "FROM product p, receipt_detail d \n " +
                     "WHERE p.idproduct = d.product_id And d.receipt_id = ? \n  "+
                     "GROUP BY d.receipt_id";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet resultSet = stm.executeQuery();
        while(resultSet.next()){
            total = resultSet.getFloat("soLuong");
        }
        String s1 = "UPDATE receipt SET total = ? WHERE idreceipt = ?";
        PreparedStatement ps = conn.prepareStatement(s1);
        ps.setFloat(1, total);
        ps.setInt(2, id);
        
        int row = ps.executeUpdate();
        
        conn.close();
        return row > 0;
    }
    
    public boolean deleteReceipt(int id) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        String s1 = "DELETE FROM receipt_detail WHERE receipt_id = ?";
        PreparedStatement ps = conn.prepareStatement(s1);
        ps.setInt(1, id);
        int rs = ps.executeUpdate();
        String sql = "DELETE FROM receipt WHERE idreceipt = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, id);
        int row = stm.executeUpdate();
        return row > 0 && rs > 0 ;
    }
    
    public List<Receipt> SearchReceiptById(int kw) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        String sql = "SELECT * FROM receipt WHERE idreceipt = ?" ;
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
            System.out.printf("%d\t\t%d\t%d\t%d\t",
                               r.getId(),r.getCreateDate(),r.getCustomer_id(),r.getStaff_id(),r.getTotal());
            
        }
        conn.close();
        return re;
    }
}
