/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.ProductService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author Admin
 */
public class ProductTester {
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
    public void testSearchProductByName(){
        try {
            ProductService p = new ProductService(conn);
            List<Product> products = p.searchByName("samsung");
            
            products.forEach(pro -> {
             Assertions.assertTrue(pro.getName().toLowerCase().contains("samsung"));
            });
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void testSearchProductByPrice(){
        try {
            ProductService p = new ProductService(conn);
            BigDecimal fromPrice = new BigDecimal(18);
            BigDecimal toPrice = new BigDecimal(20);
            List<Product> products = p.searchByPrice(fromPrice, toPrice);
            
            
            products.forEach(pro -> {
                Assertions.assertTrue(pro.getPrice().compareTo(toPrice)  < 0 || pro.getPrice().compareTo(toPrice) == 0); 
                Assertions.assertTrue(pro.getPrice().compareTo(fromPrice) > 0 || pro.getPrice().compareTo(fromPrice) == 0);
            });
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testAddProduct(){
           Product p = new Product();
           p.setName("Oppo Reno5");
           p.setPrice(new BigDecimal(21));
           p.setQuantity(20);
           p.setCategoryid(1);
           
           ProductService s = new ProductService(conn);
           Assertions.assertTrue(s.addProduct(p));
    }
    @Test
    public void testDeleteProduct(){
        ProductService s = new ProductService(conn);
        Assertions.assertTrue(s.deleteProduct(7));
    }
    
    @Test
    public void testUpdateProduct(){
        Product p = new Product();
        p.setName("Notepad");
        p.setPrice(new BigDecimal(28));
        p.setCategoryid(3);
        p.setId(6);
        
        ProductService pro = new ProductService(conn);
        Assertions.assertTrue(pro.updateProduct(p));
    }
    @Test
    public void testPriceDecrease(){
        try {
            ProductService p = new ProductService(conn);
            
            List<Product> products = p.decreases();
            
            for (int i = 0; i < products.size();i++)
                for (int j = i + 1; j < products.size();j++)
                    Assertions.assertTrue(products.get(i).getPrice().compareTo(products.get(j).getPrice()) >0
                    || products.get(i).getPrice().compareTo(products.get(j).getPrice()) == 0);
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
     @Test
    public void testPriceIncrease(){
        try {
            ProductService p = new ProductService(conn);
            List<Product> products = p.increases();
        
            for (int i = 0; i < products.size();i++)
                for (int j = i + 1; j < products.size();j++)
                    Assertions.assertTrue(products.get(i).getPrice().compareTo(products.get(j).getPrice()) < 0
                    || products.get(i).getPrice().compareTo(products.get(j).getPrice()) == 0);
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
