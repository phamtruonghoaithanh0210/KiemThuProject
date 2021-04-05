/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Receipt;
import java.sql.Connection;
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
            r.setAddressShip(rs.getString("addressship"));
            r.setAmount(rs.getBigDecimal("amount"));
            r.setPhone(rs.getString("phone"));
            r.setTotal(rs.getFloat("total"));
            r.setCreateDate(rs.getDate("created_date"));
            r.setStaff_id(rs.getInt("staff_id"));
            r.setCustomer_id(rs.getInt("customer_id"));
            
            re.add(r);
        }
        
        conn.close();
        return re;
    }
}
