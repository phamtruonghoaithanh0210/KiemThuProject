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
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.Duration;
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
           p.setDescription("modern, beautiful");
           p.setImage_link("C:\\Users\\Admin\\Desktop\\dt9.jpg");
           ProductService s = new ProductService(conn);
           Assertions.assertTrue(s.addProduct(p));
    }

 
    @Test
    public void testAddProductNameNull(){
         Product p = new Product();
           p.setName(null);
           p.setPrice(new BigDecimal(21));
           p.setQuantity(20);
           p.setCategoryid(1);
           p.setDescription("modern, beautiful");
           p.setImage_link("C:\\Users\\Admin\\Desktop\\dt9.jpg");
           ProductService s = new ProductService(conn);
           Assertions.assertFalse(s.addProduct(p));
    }
    
    @Test
    public void testAddProductPriceNull(){
         Product p = new Product();
           p.setName("SamsungGalaxyJ3");
           p.setPrice(null);
           p.setQuantity(20);
           p.setCategoryid(1);
           p.setDescription("modern, beautiful");
           p.setImage_link("C:\\Users\\Admin\\Desktop\\dt9.jpg");
           ProductService s = new ProductService(conn);
           Assertions.assertFalse(s.addProduct(p));
    }
    
    @Test
    public void testAddProductInvalidPrice(){
         Product p = new Product();
           p.setName("SamsungGalaxyJ3");
           p.setPrice(new BigDecimal(-25000000));
           p.setQuantity(20);
           p.setCategoryid(1);
           p.setDescription("modern, beautiful");
           p.setImage_link("C:\\Users\\Admin\\Desktop\\dt9.jpg");
           ProductService s = new ProductService(conn);
           Assertions.assertFalse(s.addProduct(p));
    }
    
    @Test
    public void testAddProductInvalidCate() {
        Product p = new Product();
        p.setName("iPhoneXXX");
        p.setPrice(new BigDecimal(1000000));
        p.setCategoryid(999);

        ProductService s = new ProductService(conn);
        Assertions.assertFalse(s.addProduct(p));
        
    }
    
      @Test
    public void testAddProductInvalidQuantity(){
           Product p = new Product();
           p.setName("Oppo Reno5");
           p.setPrice(new BigDecimal(21));
           p.setQuantity(-20);
           p.setCategoryid(1);
           p.setDescription("modern, beautiful");
           p.setImage_link("C:\\Users\\Admin\\Desktop\\dt9.jpg");
           ProductService s = new ProductService(conn);
           Assertions.assertFalse(s.addProduct(p));
    }
    
    @Test
    public void testUpdateProduct(){
        Product p = new Product();
        p.setName("Notepad");
        p.setPrice(new BigDecimal(28000000));
        p.setCategoryid(3);
        p.setId(6);
        p.setQuantity(10);
        p.setDescription("beautiful");
        p.setImage_link("C:\\Users\\Admin\\Desktop\\dt8.jpg");
        
        ProductService pro = new ProductService(conn);
        Assertions.assertTrue(pro.updateProduct(p));
    }
    
    @Test
    public void testUpdateProductNameNull(){
        Product p = new Product();
        p.setName(null);
        p.setPrice(new BigDecimal(28000000));
        p.setCategoryid(3);
        p.setId(6);
        p.setQuantity(10);
        p.setDescription("beautiful");
        p.setImage_link("C:\\Users\\Admin\\Desktop\\dt8.jpg");
        
        ProductService pro = new ProductService(conn);
        Assertions.assertFalse(pro.updateProduct(p));
    }
    
    @Test
    public void testUpdateProductPriceNull(){
        Product p = new Product();
        p.setName("OppoRenoX");
        p.setPrice(null);
        p.setCategoryid(3);
        p.setId(6);
        p.setQuantity(10);
        p.setDescription("beautiful");
        p.setImage_link("C:\\Users\\Admin\\Desktop\\dt8.jpg");
        
        ProductService pro = new ProductService(conn);
        Assertions.assertFalse(pro.updateProduct(p));
    }
    
    @Test
    public void testUpdateProductInvalidQuantity(){
        Product p = new Product();
        p.setName("Notepad");
        p.setPrice(new BigDecimal(28000000));
        p.setCategoryid(3);
        p.setId(6);
        p.setQuantity(-69);
        p.setDescription("beautiful");
        p.setImage_link("C:\\Users\\Admin\\Desktop\\dt8.jpg");
        
        ProductService pro = new ProductService(conn);
        Assertions.assertFalse(pro.updateProduct(p));
    }
    
     @Test
    public void testUpdateProductInvalidPrice(){
        Product p = new Product();
        p.setName("Notepad");
        p.setPrice(new BigDecimal(-25000000));
        p.setCategoryid(3);
        p.setId(6);
        p.setQuantity(20);
        p.setDescription("beautiful");
        p.setImage_link("C:\\Users\\Admin\\Desktop\\dt8.jpg");
        
        ProductService pro = new ProductService(conn);
        Assertions.assertFalse(pro.updateProduct(p));
    }
    
     @Test
    public void testUpdateProductInvalidCate(){
        Product p = new Product();
        p.setName("Notepad");
        p.setPrice(new BigDecimal(28000000));
        p.setCategoryid(999);
        p.setId(6);
        p.setQuantity(10);
        p.setDescription("beautiful");
        p.setImage_link("C:\\Users\\Admin\\Desktop\\dt8.jpg");
        
        ProductService pro = new ProductService(conn);
        Assertions.assertFalse(pro.updateProduct(p));
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
