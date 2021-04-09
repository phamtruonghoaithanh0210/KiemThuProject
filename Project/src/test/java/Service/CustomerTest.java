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
        CustomerService s = new CustomerService(JdbcUtils.getconn());
        Customer customer = s.searchByID(id);
        Assertions.assertNull(customer);
    }

    @Test
    public void testSearchbyIdIsNotNUll() throws SQLException {
        CustomerService s = new CustomerService(JdbcUtils.getconn());
        Assertions.assertNotNull(s.searchByID(31));
    }

    @ParameterizedTest
    @CsvSource({"yen1,chuheo25@gmail.com,abc.jpg,true,2012-1-1,2012-1-1,0945430117,thanh hoa,Customer,true"})
    public void testAddCustormerParams(String name, String email, String avatar, Boolean gender, Date birthDate, Date createDate, String phone, String address, User.Role userRole, boolean expected) throws SQLException {
        CustomerService s = new CustomerService(JdbcUtils.getconn());
        Customer c = new Customer(name, email, avatar, gender, birthDate, createDate, phone, address, userRole);
        Assertions.assertEquals(expected, s.addCustormer(c));
    }

    @Test
    public void testDeleteCustomer() throws SQLException {
        CustomerService s = new CustomerService(JdbcUtils.getconn());
        int id = 31;
        Boolean test1 = s.deteteCustomerByID(id);
        Boolean test2 = s.searchByID(id) == null;
        Assertions.assertTrue(Objects.equals(test1, test2));
    }

    /**
     *
     * @param name
     * @param email
     * @param avatar
     * @param gender
     * @param birthDate
     * @param createDate
     * @param phone
     * @param address
     * @param userRole
     * @param expected
     * @throws SQLException
     */
    @ParameterizedTest
    @CsvSource({"tuy my,chuheo25@gmail.com,abc.jpg,true,2000-2-1,2020-1-1,0945430117,vung tau,Customer,true"})
    public void testUpdateCustormerParams(String name, String email, String avatar, Boolean gender, Date birthDate, Date createDate, String phone, String address, User.Role userRole, boolean expected) throws SQLException {
        CustomerService c = new CustomerService(JdbcUtils.getconn());
        int id = 32;
        Customer customerUpdate = new Customer(name, email, avatar, gender, birthDate, createDate, phone, address, userRole);
        customerUpdate.setIduser(id);
        c.updateCustomer(customerUpdate);
        Customer customerInSql = c.searchByID(id);
        Assertions.assertEquals(customerUpdate.getName(), customerInSql.getName());
        Assertions.assertEquals(customerUpdate.getEmail(), customerInSql.getEmail());
        Assertions.assertEquals(customerUpdate.getAvatar(), customerInSql.getAvatar());
        Assertions.assertEquals(customerUpdate.isGender(), customerInSql.isGender());
        Assertions.assertEquals(customerUpdate.getBirthday().toString(), customerInSql.getBirthday().toString());
        Assertions.assertEquals(customerUpdate.getCreatDate().toString(), customerInSql.getCreatDate().toString());
        Assertions.assertEquals(customerUpdate.getPhone(), customerInSql.getPhone());
        Assertions.assertEquals(customerUpdate.getAddress(), customerInSql.getAddress());
    }

    @Test
    public void testGet() {
        try {
            CustomerService c = new CustomerService(conn);
            List<Customer> cates = c.getCustomer();
            Assertions.assertTrue(cates.size() >= 1);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
