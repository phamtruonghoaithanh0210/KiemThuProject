/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.User;
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
 * @author ASUS
 */
public class CustomerService {

    private Connection conn;

    public CustomerService(Connection conn) {
        this.conn = conn;
    }
    

    //add
    public Boolean addCustormer(Customer customer) throws SQLException  {
        //khai bao cau lenh de them vao bang
        String insertUserSql = "INSERT INTO user (`name`, `email`,  `avatar`, `gender`, `birthday`, `create_date`, `phone`, `address`, `user_role`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String insertCusSql = "INSERT INTO customer (`idcustomer`) VALUES (?);";
        //ta ket noi
        Connection conn;
        conn = this.conn;
        try {
            conn.setAutoCommit(false);
             //khai bao bien de thu hien truy van
             PreparedStatement preparedStatement = conn.prepareStatement(insertUserSql);
             //truyen cac tham so
             preparedStatement.setString(1, customer.getName());
             preparedStatement.setString(2, customer.getEmail());
             preparedStatement.setString(3, customer.getAvatar());
             preparedStatement.setBoolean(4, customer.isGender());
             preparedStatement.setDate(5, customer.getBirthday());
             preparedStatement.setDate(6, customer.getCreatDate());
             preparedStatement.setString(7, customer.getPhone());
             preparedStatement.setString(8, customer.getAddress());
             preparedStatement.setString(9, customer.getUserRole().toString());
             //dung gia tac neu truy van 1 thanh cong qua truy van 2
             
             preparedStatement.executeUpdate();
             Statement  stm = conn.createStatement();
             System.out.println("ok");
               //thuc hien lay id của user để thêm vào bảng csdl
             ResultSet rs = stm.executeQuery("SELECT * FROM user ORDER BY iduser Desc LIMIT 1;");
                while(rs.next()){
                    customer.setIduser(rs.getInt("iduser"));
                }
             PreparedStatement pSStaff = conn.prepareStatement(insertCusSql);
             //truyen cac tham so
             pSStaff.setInt(1,customer.getIduser());
             pSStaff.executeUpdate();
            conn.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
        }
        return true;
    }
    public List<Customer> getCustomer() throws SQLException {
        Connection conn = this.conn;
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
        Connection conn = this.conn;
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
        //delete
        public boolean deteteCustomerByID(int id) throws SQLException{
        Connection conn = this.conn;
        Boolean result = false;
        if(this.searchByID(id)==null)
            return false;
         else {
            try {
                conn.setAutoCommit(false);
                //delete  in table customer
                String stableCustomer = "DELETE FROM customer WHERE (`idcustomer` = ?);";
                PreparedStatement preCutormer = conn.prepareStatement(stableCustomer);
                preCutormer.setInt(1, id);
                preCutormer.executeUpdate();
                //delete ò in table user
                String s = "DELETE FROM user WHERE (`iduser` = ?);";
                PreparedStatement pre = conn.prepareStatement(s);
                pre.setInt(1, id);
                pre.executeUpdate();
  
                result = true;
                conn.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
        }
        }
        return result;
    }
     //update
    //update
    public Boolean updateCustomer(Customer customer) throws SQLException  {
            Boolean result = false;
            if(this.searchByID(customer.getIduser())==null){
                result = false;
                System.out.println(customer.getIduser());
            } else {
                //khai bao cau lenh de update vao bang
                String insertUserSql = "UPDATE user SET `name` = ?, `email` = ?, `avatar` = ?, `gender` = ?, `birthday` = ?, `create_date` = ?, `phone` = ?, `address` = ?, `user_role` = 'Staff' WHERE (`iduser` = ?);";
                //ta ket noi
                Connection conn;
                conn = this.conn;
                try {
                     //khai bao bien de thu hien truy van
                     PreparedStatement preparedStatement = conn.prepareStatement(insertUserSql);
                     //truyen cac tham so
                     preparedStatement.setString(1, customer.getName());
                     preparedStatement.setString(2, customer.getEmail());
                     preparedStatement.setString(3, customer.getAvatar());
                     preparedStatement.setBoolean(4, customer.isGender());
                     preparedStatement.setDate(5, customer.getBirthday());
                     preparedStatement.setDate(6, customer.getCreatDate());
                     preparedStatement.setString(7, customer.getPhone());
                     preparedStatement.setString(8, customer.getAddress());
                     preparedStatement.setInt(9, customer.getIduser());
                     preparedStatement.executeUpdate();
                     result = true;

                } catch (SQLException ex) {
                    Logger.getLogger(StaffService.class.getName()).log(Level.SEVERE, null, ex);
                    conn.rollback();
                }
            }
            return result;
    }
}
