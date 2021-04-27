/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Category;
import com.kiemthu.pojo.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProductService {
    private Connection conn;
    private List<Product> ds = new ArrayList<>();
    
    public ProductService(Connection conn){
        this.conn = conn;
    }



    
    //Add product to list-products
    public boolean addProduct (Product p){
        try {
            String s = "INSERT INTO Product (name, price,categoryid,quatity,description )VALUES(?,?,?,?,?)";
            PreparedStatement pre = this.conn.prepareStatement(s);
            pre.setString(1,p.getName());
            pre.setBigDecimal(2, p.getPrice());
            pre.setInt(3, p.getCategoryid());
            pre.setInt(4, p.getQuantity());
            pre.setString(5, p.getDescription());
            int rs = pre.executeUpdate();
            return rs > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //Delete product in list-products by id_product
    public boolean deleteProduct (int id){
        try {
            String s = "DELETE FROM product WHERE idproduct = ?";
            PreparedStatement pre = this.conn.prepareStatement(s);
            pre.setInt(1, id);
            
            int rs = pre.executeUpdate();
            return rs > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //Update information of product in list-products by id_product
    public boolean updateProduct (Product p){
        try {
            String s = " UPDATE product SET name = ?, price = ?, categoryid = ?, quatity = ?,description = ? WHERE idproduct = ?";
            PreparedStatement pre = this.conn.prepareStatement(s);
            pre.setString(1, p.getName());
            pre.setBigDecimal(2, p.getPrice());
            pre.setInt(3, p.getCategoryid());
            pre.setInt(4,p.getQuantity());
            pre.setString(5, p.getDescription());
            pre.setInt(6, p.getId());
            
            int rs = pre.executeUpdate();
            return rs > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //Sort increase data table by price 
    public List<Product> increases() throws SQLException{

            String s = "SELECT * FROM product ORDER BY price  ";
            PreparedStatement pre = this.conn.prepareStatement(s);
            ResultSet rs = pre.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()){
                Product p = new Product();
                p.setId(rs.getInt("idproduct"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setQuantity(rs.getInt("quatity"));
                p.setDescription(rs.getString("description"));
                
                products.add(p);
               
                
            }
        return products;
        
    }
    //Sort decrese data table by price
    public List<Product> decreases() throws SQLException{
            String s = "SELECT * FROM product ORDER BY price desc";
            PreparedStatement pre = this.conn.prepareStatement(s);
            ResultSet rs = pre.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()){
                Product p = new Product();
                p.setId(rs.getInt("idproduct"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setQuantity(rs.getInt("quatity"));
                p.setDescription(rs.getString("description"));
                products.add(p);
               
                
            }
        return products;
    }
    //Search product by name_product
    public List<Product> searchByName (String kw) throws SQLException{
        if (kw ==  null)
                throw new SQLDataException();
   
        String s = "SELECT * FROM product WHERE name LIKE CONCAT ('%',?,'%')";
        PreparedStatement pre = this.conn.prepareStatement(s);
        pre.setString(1, kw);
            
        ResultSet rs = pre.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()){
            Product p = new Product();
            p.setId(rs.getInt("idproduct"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setCategoryid(rs.getInt("categoryid"));
            p.setDescription(rs.getString("description"));
            p.setQuantity(rs.getInt("quatity"));
            products.add(p);
            //System.out.printf("\n%d\t%s\t\t%s",    p.getId(),p.getName(),p.getPrice());
            
        }
        return products;
    }
    //Search product by_price
    public List<Product> searchByPrice(BigDecimal fromPrice, BigDecimal toPrice) throws SQLException{
        if (fromPrice ==  null && toPrice == null)
           throw new SQLDataException();
        String s = "SELECT * FROM product WHERE price BETWEEN ? AND ? ";
        PreparedStatement pre = this.conn.prepareStatement(s);
        pre.setBigDecimal(1, fromPrice);
        pre.setBigDecimal(2, toPrice);
            
        ResultSet rs = pre.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()){
            Product p = new Product();
            p.setId(rs.getInt("idproduct"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setQuantity(rs.getInt("quatity"));
            p.setCategoryid(rs.getInt("categoryid"));
            p.setDescription(rs.getString("description"));
            products.add(p);
           // System.out.printf("\n%d\t%s\t\t%s",   p.getId(),p.getName(),p.getPrice());
            
        }
        return products;
    }
    //Get Product
     public List<Product> getPros() throws SQLException {
        Connection conn = JdbcUtils.getconn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM product");
        
        List<Product> pro = new ArrayList<>();
        while (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("idproduct"));
            p.setName(rs.getString("name"));
         
            pro.add(p);
        }
        
        conn.close();
        return pro;
    }
   //Get product by Id
     public Product getProductById(int id) throws SQLException{
      
        String sql = "SELECT * FROM product WHERE idproduct = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        Product p = null;
        while (rs.next()){
               p = new Product();
               p.setId(rs.getInt("idproduct"));
               p.setName(rs.getString("name"));
               p.setPrice(rs.getBigDecimal("price"));
               p.setQuantity(rs.getInt("quatity"));
               p.setId(rs.getInt("categoryid"));
               p.setDescription(rs.getString("description"));
        }
        return p;
    }
}

