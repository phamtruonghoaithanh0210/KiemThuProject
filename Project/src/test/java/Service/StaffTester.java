/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.service.StaffService;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ASUS
 */
public class StaffTester {
    @Test
    public void testSearchbyId() throws SQLException {
        int id = 5;
        StaffService s = new StaffService();
        Staff staff = s.searchByID(id);
        Assertions.assertNull(staff);
        Assertions.assertNotNull(s.searchByID(25));
    }
    @Test
    public void testSearchbyIdISNOLLNUll() throws SQLException {
        StaffService s = new StaffService();
        Assertions.assertNotNull(s.searchByID(25));
    }
}
