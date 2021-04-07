/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Category;
import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class StaffService {
        public List<Staff> getStaffs() throws SQLException {
        Connection conn = JdbcUtils.getconn();
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
            staffs .add(c);
        }

        conn.close();
        return staffs;
    }
}
