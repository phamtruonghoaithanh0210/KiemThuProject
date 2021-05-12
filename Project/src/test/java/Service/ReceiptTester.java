
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.kiemthu.pojo.Receipt;
import com.kiemthu.pojo.Receipt_Detail;
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
import org.junit.jupiter.api.DisplayName;

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
            r.setCustomer_id(2);
            
            ReceiptService re = new ReceiptService();
            Assertions.assertTrue(re.addReceipt(r));
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testSearchReceiptWithId() throws SQLException{
        String id = "1";
        ReceiptService re = new ReceiptService();
         List<Receipt> receipt = re.SearchReceiptById(id);
        Assertions.assertTrue(receipt.size()== 1);
    }
    
    @Test
    public void testSearchReceiptWithIvalidId() throws SQLException{
        String id = "10000";
        ReceiptService re = new ReceiptService();
         List<Receipt> receipt = re.SearchReceiptById(id);
        Assertions.assertFalse(receipt.size()== 1);
    }
    
    @Test
    @DisplayName("Kiem thu chuc nang them sản phẩm vào hóa đơn ")
    public void TestAddReceiptDetail(){
        try {
            ReceiptService r = new ReceiptService();
            //id = 2 ,quantity =5 
            Assertions.assertTrue(r.addReceipt_Detail(2,5));
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    @DisplayName("Kiem thu chuc nang them sản phẩm vào hóa đơn với Quanlity vượt hơn số lượng hiện có")
    public void TestAddReceiptDetailWithQuantity(){
        try {
            ReceiptService r = new ReceiptService();
            //id = 1,quantity = 5000
            Assertions.assertFalse(r.addReceipt_Detail(1,5000));
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Test
    @DisplayName("Kiem thu chuc nang them sản phẩm vào hóa đơn với ID sản phẩm null")
    public void TestAddReceiptDetailWithIdNull(){
        try {
            ReceiptService r = new ReceiptService();
            //id = 0,quantity = 5000
            Assertions.assertFalse(r.addReceipt_Detail(1,5000));
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
     @Test
    @DisplayName("Kiem thu chuc nang them sản phẩm vào hóa đơn với Quantity = 0")
    public void TestAddReceiptDetailWithQuantityNull(){
        try {
            ReceiptService r = new ReceiptService();
            //id = 1,quantity = 0
            Assertions.assertFalse(r.addReceipt_Detail(1,0));
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
