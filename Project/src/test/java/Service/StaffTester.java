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
    public void testSearchbyIdIsNull() throws SQLException {
        int id = 5;
        StaffService s = new StaffService();
        Staff staff = s.searchByID(id);
        Assertions.assertNull(staff);
    }
    @Test
    public void testSearchbyIdIsNotNUll() throws SQLException {
        StaffService s = new StaffService();
        Assertions.assertNotNull(s.searchByID(26));
    }
    @Test
    public void testDeleteStaff() throws SQLException { 
        StaffService s = new StaffService();
        int id = 26;
        Boolean test1 = s.deteteStaffByID(0);
        Boolean test2 = s.searchByID(26)==null;
        Assertions.assertTrue(test1==test2);
    }
}
