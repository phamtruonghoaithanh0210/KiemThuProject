/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.kiemthu.pojo.Receipt;
import com.kiemthu.pojo.service.ReceiptService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
}
