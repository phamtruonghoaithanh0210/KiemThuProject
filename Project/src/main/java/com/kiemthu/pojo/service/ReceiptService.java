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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if (newQuantity >= 5){
            String sql = "INSERT INTO receipt_detail(product_id,quantity,receipt_id) VALUES(?,?,?)"  ;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, productid);
            stm.setInt(2, quantity);
            stm.setInt(3, id);
            stm.executeUpdate();
            String s2 = "UPDATE product SET quantity = ? WHERE idproduct = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(s2);
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, productid);
            int row = preparedStatement.executeUpdate();
            return row >0 ;
        }
        conn.close();
        return false ;      
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
        
        String s2 = "Delete from receipt where not exists (select * from receipt_detail where receipt.idreceipt = receipt_detail.receipt_id)";
        PreparedStatement preparedStatement = conn.prepareStatement(s2);
        preparedStatement.executeUpdate();
        
        int row = ps.executeUpdate();
        return row > 0;
    }
    
    public boolean deleteReceipt() throws SQLException{
        Connection conn = JdbcUtils.getconn();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT idreceipt FROM receipt ORDER BY idreceipt desc LIMIT 1 ");
        rs.absolute(1);
        int id = rs.getInt(1);
        String s1 = "DELETE FROM receipt_detail WHERE receipt_id = ?";
        PreparedStatement ps = conn.prepareStatement(s1);
        ps.setInt(1, id);
        int rs1 = ps.executeUpdate();
        String sql = "DELETE FROM receipt WHERE idreceipt = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, id);
        int row = stm.executeUpdate();

        return row > 0 && rs1 > 0 ;
    }
    
    public List<Receipt> SearchReceiptById(String kw) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        String sql = "SELECT * FROM receipt WHERE idreceipt = ?" ;
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, kw);
        
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
        }
        return re;
    }
    
    public List<Receipt_Detail> SeachByIDreceipt(String id) throws SQLException{
   
            Connection conn = JdbcUtils.getconn();
            String sql = "SELECT * FROM receipt_detail WHERE receipt_id  = ?" ;       
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            List<Receipt_Detail> re = new ArrayList<>();
            while(rs.next()){
                Receipt_Detail r = new Receipt_Detail();
                r.setId(rs.getInt("idreceipt_detail"));
                r.setProductid(rs.getInt("product_id"));
                r.setQuantity(rs.getInt("quantity"));
                r.setReceiptid(rs.getInt("receipt_id"));
                re.add(r);         
            }
            return re;
    }
    
        public List<Receipt_Detail> getReceipt_Detail() throws SQLException{
        Connection conn = JdbcUtils.getconn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM receipt_detail");
        
        List<Receipt_Detail> re =  new ArrayList<>();
        while(rs.next()){

            Receipt_Detail r = new Receipt_Detail();
            r.setId(rs.getInt("idreceipt_detail"));
            r.setProductid(rs.getInt("product_id"));
            r.setQuantity(rs.getInt("quantity"));
            r.setReceiptid(rs.getInt("receipt_id"));
            re.add(r);
        }
        return re;
    }
}
