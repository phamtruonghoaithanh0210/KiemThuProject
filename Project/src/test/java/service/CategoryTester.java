package service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */


import com.kiemthu.pojo.Category;
import com.kiemthu.pojo.service.CategoryService;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author acer
 */
public class CategoryTester {
    @Test
    public void testQuantity() throws SQLException {
        List<Category> cates = new CategoryService().getCates();
        Assertions.assertTrue(cates.size() >= 2);
    }
}