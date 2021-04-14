/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.kiemthu.pojo.Staff;

import com.kiemthu.pojo.User.Role;

import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.StaffService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author ASUS
 */
public class StaffTester {

    private static Connection conn;

    @BeforeAll
    public static void setUpClass() {
        try {
            conn = JdbcUtils.getconn();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testSearchbyIdIsNull() throws SQLException {
        int id = 0;
        StaffService s = new StaffService(JdbcUtils.getconn());
        Staff staff = s.searchByID(id);
        Assertions.assertNull(staff);
    }

    @Test
    public void testSearchbyIdIsNotNUll() throws SQLException {
        StaffService s = new StaffService(JdbcUtils.getconn());
        Assertions.assertNotNull(s.searchByID(39));
    }
    @ParameterizedTest
    @CsvSource({"yencute,chuheo25@gmail.com1,voyen1,1816101,abc.jpg1,false,2000-1-1,2021-1-1,09454301171,thanh hoa1,Staff,true"})
    public void testAddCustormerParams(String name, String email, String username, String password, String avatar, Boolean gender, Date birthDate, Date createDate, String phone, String address, Role userRole, Boolean expected) throws SQLException {
        StaffService s = new StaffService(conn);
        Staff staff = new Staff(name, email, username, password, avatar, gender, birthDate, createDate, phone, address, userRole);
        Assertions.assertEquals(expected, s.addStaff(staff));
    }

    @ParameterizedTest
    @CsvSource({"yencute,chuheo25@gmail.com1,voyen1,1816101,abc.jpg1,false,2000-1-1,2021-1-1,09454301171,thanh hoa1,Staff"})
    public void testupdateStaff(String name, String email, String username, String password, String avatar, Boolean gender, Date birthDate, Date createDate, String phone, String address, Role userRole) throws SQLException {
        System.out.println("service.StaffTester.testupdateStaff()");
        int id = 39;
        StaffService s = new StaffService(JdbcUtils.getconn());
        Staff staffUpdate = new Staff(name, email, username, password, avatar, gender, birthDate, createDate, phone, address, userRole);
        staffUpdate.setIduser(id);
        
        s.UpdateStaff(staffUpdate);
        System.out.println("oke");
        Staff staffInSql = s.searchByID(id);
        System.out.println("service.StaffTester.testupdateStaff()");
        Assertions.assertEquals(staffUpdate.getName(), staffInSql.getName());
        Assertions.assertEquals(staffUpdate.getEmail(), staffInSql.getEmail());
        System.out.println("service.StaffTester.testupdateStaff()");
        Assertions.assertEquals(staffUpdate.getUsername(), staffInSql.getUsername());
        Assertions.assertEquals(staffUpdate.getPassword(), staffInSql.getPassword());
        Assertions.assertEquals(staffUpdate.getAvatar(), staffInSql.getAvatar());
        Assertions.assertEquals(staffUpdate.isGender(), staffInSql.isGender());
        Assertions.assertEquals(staffUpdate.getBirthday().toString(), staffInSql.getBirthday().toString());
        Assertions.assertEquals(staffUpdate.getCreatDate().toString(), staffInSql.getCreatDate().toString());
        Assertions.assertEquals(staffUpdate.getPhone(), staffInSql.getPhone());
        Assertions.assertEquals(staffUpdate.getAddress(), staffInSql.getAddress());
    }
    @Test
    public void testDeleteStaff() throws SQLException {
        StaffService s = new StaffService(JdbcUtils.getconn());
        int id = 40;
        Boolean test1 = s.deteteStaffByID(id);
        Boolean test2 = s.searchByID(id) == null;
        Assertions.assertTrue(Objects.equals(test1, test2));
    }
    @Test
    public void testGet() {
        try {
            StaffService s = new StaffService(conn);
            List<Staff> cates = s.getStaffs();
            Assertions.assertTrue(cates.size() >= 1);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

