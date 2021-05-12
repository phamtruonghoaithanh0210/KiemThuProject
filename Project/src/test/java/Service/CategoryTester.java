/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.service.CategoryService;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.ProductService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Locale.Category;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.ParameterizedTest;


public class CategoryTester {
    private static Connection conn;
    
    @BeforeAll //Chạy trước tất cả các phương thức test case, phương thức có annotation này phải là static.
    public static void setUpClass(){
        try {
            conn = JdbcUtils.getconn();
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll  //Chạy sau tất cả các phương thức test case, phương thức có annotation này phải là static.
    public static void tearDownClass(){
        if (conn!= null)
            try {
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testException() {
        Assertions.assertThrows(SQLDataException.class, () -> {
            new ProductService(conn).searchByName(null);
        });
    }

    @Test
    public void testTimeout() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            new ProductService(conn).searchByName("");
        });
    }
    @Test
    public void testQuantity() {
        try {
            
            CategoryService s = new CategoryService();
            Assertions.assertTrue(s.getCates().size() >= 3);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @DisplayName("Thêm danh mục sản phẩm đã có")
    public void testAddCategoryExist() throws SQLException {
       
        CategoryService s = new CategoryService();
        Assertions.assertFalse(s.addCategory("Laptop"));
    }
    
     @Test
    @DisplayName("Thêm danh mục sản phẩm ")
    public void testAddCategory() throws SQLException {
       
        CategoryService s = new CategoryService();
        Assertions.assertTrue(s.addCategory("Headphone"));
    }
    
    @Test
    @DisplayName("Thêm danh mục sản phẩm tên danh mục null")
    public void testAddCategoryNameNull() throws SQLException {
       
        CategoryService s = new CategoryService();
        Assertions.assertFalse(s.addCategory(null));
    }
}