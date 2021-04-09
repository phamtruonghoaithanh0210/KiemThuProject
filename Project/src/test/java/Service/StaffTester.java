/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.User;
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
        Assertions.assertNotNull(s.searchByID(28));
    }

    @Test
    public void testupdateStaff() throws SQLException { 
        int id = 29;
        StaffService s = new StaffService();
        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis); 
        Staff staffUpdate = new Staff("yencute","chuheo25@gmail.com1","voyen1","1816101","abc.jpg1",false,date,date,"09454301171","thanh hoa1",User.Role.Staff);
        staffUpdate .setIduser(id);
        s.UpdateStaff(staffUpdate );
        Staff staffInSql = s.searchByID(id);
        Assertions.assertEquals(staffUpdate.getName() , staffInSql.getName());
        Assertions.assertEquals(staffUpdate.getEmail() , staffInSql.getEmail());
        Assertions.assertEquals(staffUpdate.getUsername() , staffInSql.getUsername());
        Assertions.assertEquals(staffUpdate.getPassword() , staffInSql.getPassword());
        Assertions.assertEquals(staffUpdate.getAvatar() , staffInSql.getAvatar());
        Assertions.assertEquals(staffUpdate.isGender() , staffInSql.isGender());
        Assertions.assertEquals(staffUpdate.getBirthday().toString() , staffInSql.getBirthday().toString());
        Assertions.assertEquals(staffUpdate.getCreatDate().toString() , staffInSql.getCreatDate().toString());
        Assertions.assertEquals(staffUpdate.getPhone() , staffInSql.getPhone());
        Assertions.assertEquals(staffUpdate.getAddress() , staffInSql.getAddress());
    }
    @Test
    public void testDeleteStaff() throws SQLException { 
        StaffService s = new StaffService();
        int id = 29;
        Boolean test1 = s.deteteStaffByID(id);
        Boolean test2 = s.searchByID(id)==null;
        Assertions.assertTrue(test1==test2);
    }
}
