/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;


import com.kiemthu.pojo.Staff;
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
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author ASUS
 */
public class StaffService {
    //khai bao khoi tao
    private Connection conn;

    public StaffService(Connection conn) {
        this.conn = conn;
    }
    //them
    public Boolean addStaff(Staff staff) throws SQLException {
        //khai bao cau lenh de them vao bang
        String insertUserSql = "INSERT INTO user (`name`, `email`,  `avatar`, `gender`, `birthday`, `create_date`, `phone`, `address`, `user_role`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String insertStaffSql = "INSERT INTO staff (`idStaff`,`username`, `password`) VALUES (?,?,?);";
        //ta ket noi
        Connection conn;
        conn = this.conn;
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
            Statement stm = conn.createStatement();
            //thuc hien lay id của user để thêm vào bảng csdl
            ResultSet rs = stm.executeQuery("SELECT * FROM user ORDER BY iduser Desc LIMIT 1;");
            while (rs.next()) {
                staff.setIduser(rs.getInt("iduser"));
            }
            PreparedStatement pSStaff = conn.prepareStatement(insertStaffSql);
            //truyen cac tham so
            pSStaff.setInt(1, staff.getIduser());
            pSStaff.setString(2, staff.getUsername());
            pSStaff.setString(3, DigestUtils.sha256Hex(staff.getPassword()));
            pSStaff.executeUpdate();
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(StaffService.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
        }
        return true;
    }

    public List<Staff> getStaffs() throws SQLException {
        Connection conn = this.conn;
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM user where user_role like 'Staff' ");
        List<Staff> staffs = new ArrayList<>();
        while (rs.next()) {
            // xư li bang nhan vien lay username password
            Staff c = new Staff();
            c.setIduser(rs.getInt("iduser"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setAvatar(rs.getString("avatar"));
            c.setGender(rs.getBoolean("gender"));
            c.setBirthday(rs.getDate("birthday"));
            c.setCreatDate(rs.getDate("create_date"));
            c.setPhone(rs.getString("phone"));
            c.setUserRole(User.Role.Staff);
            String s = "SELECT * FROM staff where idStaff =? ";
            PreparedStatement pre = conn.prepareStatement(s);
            pre.setInt(1, c.getIduser());
            ResultSet rsStaff = pre.executeQuery();
            while (rsStaff.next()) {
                c.setUsername(rsStaff.getString("username"));
                c.setPassword(rsStaff.getString("password"));
            }
            staffs.add(c);
        }
        conn.close();
        return staffs;
    }

    //Search staff by id 
    public Staff searchByID(int id) throws SQLException {
        Connection conn = this.conn;
        String s = "SELECT * FROM user, staff where iduser = idStaff and iduser = ?;";
        PreparedStatement pre = conn.prepareStatement(s);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        Staff staff = new Staff();
        while (rs.next()) {
            staff.setIduser(rs.getInt("iduser"));
            staff.setName(rs.getString("name"));
            staff.setEmail(rs.getString("email"));
            staff.setAvatar(rs.getString("avatar"));
            staff.setGender(rs.getBoolean("gender"));
            staff.setBirthday(rs.getDate("birthday"));
            staff.setAddress(rs.getString("address"));
            staff.setCreatDate(rs.getDate("create_date"));
            staff.setPhone(rs.getString("phone"));
            staff.setUserRole(User.Role.Staff);
            staff.setUsername(rs.getString("username"));
            staff.setPassword(rs.getString("password"));
        }
        if (staff.getName() == null) {
            return null;
        }
        return staff;
    }

    //delete
    public boolean deteteStaffByID(int id) throws SQLException {
        Connection conn = this.conn;
        Boolean result = false;
        if (this.searchByID(id) == null) {
            return false;
        } else {
            try {
                conn.setAutoCommit(false);
                //delete ò in table staff
                String stableSatff = "DELETE FROM staff WHERE (`idStaff` = ?);";
                PreparedStatement preStaff = conn.prepareStatement(stableSatff);
                preStaff.setInt(1, id);
                preStaff.executeUpdate();
                //delete ò in table user
                String s = "DELETE FROM user WHERE (`iduser` = ?);";
                PreparedStatement pre = conn.prepareStatement(s);
                pre.setInt(1, id);
                pre.executeUpdate();

                result = true;
                conn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(StaffService.class.getName()).log(Level.SEVERE, null, ex);
                conn.rollback();
            }
        }
        return result;
    }

    //update
    public Boolean UpdateStaff(Staff staff) throws SQLException {
        Boolean result = false;
        if (this.searchByID(staff.getIduser()) == null) {
            result = false;
        } else {
            //khai bao cau lenh de update vao bang
            String insertUserSql = "UPDATE user SET `name` = ?, `email` = ?, `avatar` = ?, `gender` = ?, `birthday` = ?, `create_date` = ?, `phone` = ?, `address` = ?, `user_role` = 'Staff' WHERE (`iduser` = ?);";
            String insertStaffSql = "UPDATE staff SET `username` = ?, `password` = ? WHERE (`idStaff` = ?);";
            //ta ket noi
            try {
                this.conn.setAutoCommit(false);
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
                preparedStatement.setInt(9, staff.getIduser());
                //dung gia tac neu truy van 1 thanh cong qua truy van 2

                preparedStatement.executeUpdate();
                PreparedStatement pSStaff = conn.prepareStatement(insertStaffSql);
                //truyen cac tham so
                pSStaff.setString(1, staff.getUsername());
                pSStaff.setString(2, staff.getPassword());
                pSStaff.setInt(3, staff.getIduser());
                pSStaff.executeUpdate();
                conn.commit();
                result = true;

            } catch (SQLException ex) {
                Logger.getLogger(StaffService.class.getName()).log(Level.SEVERE, null, ex);
                conn.rollback();
            }
        }
        return result;
    }

}
