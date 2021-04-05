package com.kiemthu.pojo.service;

import com.kiemthu.pojo.Category;
import com.kiemthu.pojo.service.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/


/**
 *
 * @author acer
 */
public class CategoryService {
    public List<Category> getCates() throws SQLException {
        Connection conn = JdbcUtils.getconn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM category");
        
        List<Category> cates = new ArrayList<>();
        while (rs.next()) {
            Category c = new Category();
            c.setId(rs.getInt("idcategory"));
            c.setName(rs.getString("name"));
            cates.add(c);
        }
        
        conn.close();
        return cates;
    }
}
