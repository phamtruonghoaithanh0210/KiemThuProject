<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.kiemthu.pojo.Receipt;
import com.kiemthu.pojo.service.ReceiptService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author acer
 */
public class ReceiptTester {
    private Connection conn;
    @Test
    public void testQuantity() throws SQLException {
        List<Receipt> reces  = new ReceiptService().getReceipt();
        Assertions.assertTrue(reces.size() >= 1);
    }
    
    @Test
    public void testAddReceipt(){
        try {
            Receipt r = new Receipt();
            r.setCreateDate(Date.valueOf(LocalDate.MAX));
            r.setTotal(new Float(180000));
            r.setCustomer_id(2);
            r.setStaff_id(1);
            
            ReceiptService re = new ReceiptService();
            Assertions.assertTrue(re.addReceipt(r));
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testAddReceiptwithIvalidCusId(){
        try {
            Receipt r = new Receipt();
            r.setCreateDate(Date.valueOf(LocalDate.MAX));
            r.setTotal(new Float(180000));
            r.setCustomer_id(50);
            r.setStaff_id(1);
            
            ReceiptService re = new ReceiptService();
            Assertions.assertFalse(re.addReceipt(r));
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    
    @Test
    public void testSearchReceiptWithId() throws SQLException{
        int id = 10;
        ReceiptService re = new ReceiptService();
         List<Receipt> receipt = re.SearchReceiptById(id);
        Assertions.assertTrue(receipt.size()== 1);
    }
    
    @Test
    public void testSearchReceiptWithIvalidId() throws SQLException{
        int id = 22222;
        ReceiptService re = new ReceiptService();
         List<Receipt> receipt = re.SearchReceiptById(id);
        Assertions.assertFalse(receipt.size()== 2);
    }
}
=======
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Service;
//
//import com.kiemthu.pojo.Receipt;
//import com.kiemthu.pojo.service.ReceiptService;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;
//
///**
// *
// * @author acer
// */
//public class ReceiptTester {
//    private Connection conn;
//    @Test
//    public void testQuantity() throws SQLException {
//        List<Receipt> reces  = new ReceiptService().getReceipt();
//        Assertions.assertTrue(reces.size() >= 1);
//    }
//    
//    @Test
//    public void testAddReceipt(){
//        try {
//            Receipt r = new Receipt();
//            r.setCreateDate(Date.valueOf(LocalDate.MAX));
//            r.setTotal(new Float(180000));
//            r.setCustomer_id(2);
//            r.setStaff_id(1);
//            
//            ReceiptService re = new ReceiptService();
//            Assertions.assertTrue(re.addReceipt(r));
//        } catch (SQLException ex) {
//            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//}
>>>>>>> 794e59e11882b606a409c5c3ccbea85d065bdc7a
