/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class UserService {
        public Boolean addStaff(Staff staff) throws SQLException  {
        //khai bao cau lenh de them vao bang
        String insertUserSql = "INSERT INTO `saleappphone`.`user` (`name`, `email`,  `avatar`, `gender`, `birthday`, `create_date`, `phone`, `address`, `user_role`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String insertStaffSql = "INSERT INTO `saleappphone`.`staff` (`idStaff`,`username`, `password`) VALUES (?,?,?);";
        //ta ket noi
        Connection conn;
        conn = JdbcUtils.getconn();
        try {
            conn.setAutoCommit(false);
             //khai bao bien de thu hien truy van
             PreparedStatement preparedStatement = conn.prepareStatement(insertUserSql);
             //truyen cac tham so
             preparedStatement.setString(1, staff.getName());
             preparedStatement.setString(2, staff.getEmail());
             preparedStatement.setString(3, staff.getAvatar());
             preparedStatement.setBoolean(4, staff.isGender());
             preparedStatement.setDate(5, staff.getBirthday());
             preparedStatement.setDate(6, staff.getCreatDate());
             preparedStatement.setString(7, staff.getPhone());
             preparedStatement.setString(8, staff.getAddress());
             preparedStatement.setString(9, staff.getUserRole().toString());
             //dung gia tac neu truy van 1 thanh cong qua truy van 2
             
             preparedStatement.executeUpdate();
             Statement  stm = conn.createStatement();
               //thuc hien lay id của user để thêm vào bảng csdl
             ResultSet rs = stm.executeQuery("SELECT * FROM user ORDER BY iduser Desc LIMIT 1;");
                while(rs.next()){
                    staff.setIduser(rs.getInt("iduser"));
                }
             PreparedStatement pSStaff = conn.prepareStatement(insertStaffSql);
             //truyen cac tham so
             pSStaff.setInt(1,staff.getIduser());
             pSStaff.setString(2, staff.getUsername());
             pSStaff.setString(3, staff.getPassword());
             pSStaff.executeUpdate();
            conn.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
        }
        return null;
    }
}
