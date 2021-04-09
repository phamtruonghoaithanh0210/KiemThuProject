/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.service.CustomerService;
import com.kiemthu.pojo.service.StaffService;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ASUS
 */
public class CustomerTest {
    @Test
    public void testSearchbyIdIsNull() throws SQLException {
        int id = 5;
        CustomerService s = new CustomerService();
        Customer customer = s.searchByID(id);
        Assertions.assertNull(customer);
    }
    @Test
    public void testSearchbyIdIsNotNUll() throws SQLException {
        CustomerService s = new CustomerService();
        Assertions.assertNotNull(s.searchByID(27));
    }
}
