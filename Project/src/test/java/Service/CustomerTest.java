/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.User;
import com.kiemthu.pojo.service.CustomerService;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.StaffService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
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
public class CustomerTest {
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
        int id = 5;
        CustomerService s = new CustomerService(conn);
        Customer customer = s.searchByID(id);
        Assertions.assertNull(customer);
    }
    @Test
    public void testSearchbyIdIsNotNUll() throws SQLException {
        CustomerService s = new CustomerService(conn);
        Assertions.assertNotNull(s.searchByID(27));
    }
    @ParameterizedTest
    @CsvSource({"yen1,chuheo25@gmail.com,abc.jpg,true,2012-1-1,2012-1-1,0945430117,thanh hoa,Customer,true"})
    public void testAddCustormerParams(String name, String email, String avatar, Boolean gender, Date birthDate, Date createDate, String phone, String address, User.Role userRole,boolean expected) throws SQLException {
        CustomerService s = new CustomerService(conn);
        Customer c = new Customer( name,email,avatar,gender, birthDate,createDate, phone, address, userRole);
        Assertions.assertEquals(expected, s.addCustormer(c));
    }
    @Test
    public void testDeleteCustomer() throws SQLException { 
        CustomerService s = new CustomerService(conn);
        int id = 27;
        Boolean test1 = s.deteteCustomerByID(id);
        Boolean test2 = s.searchByID(id)==null;
        Assertions.assertTrue(test1==test2);
    }
}
