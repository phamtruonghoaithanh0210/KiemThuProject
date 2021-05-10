/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.service.CategoryService;
import com.kiemthu.pojo.service.CustomerService;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.ProductService;
import com.kiemthu.pojo.service.StaffService;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class BirthdayController implements Initializable {

     @FXML private TableView<Staff> tableView1;
     @FXML private TableView<Customer> tableView2;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
             loadColumns1();
             loadColumns2();
             loadPeople1();
             loadPeople2();
         } catch (SQLException ex) {
             Logger.getLogger(BirthdayController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
      private void loadColumns1(){
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("iduser"));

        TableColumn colName = new TableColumn("Name");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colBirthday = new TableColumn("Birthday");
        colBirthday.setCellValueFactory(new PropertyValueFactory("birthday"));
        
        
       

      
        this.tableView1.getColumns().addAll(colId, colName, colBirthday);
       
    }
    private void loadColumns2(){
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("iduser"));

        TableColumn colName = new TableColumn("Name");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colBirthday = new TableColumn("Birthday");
        colBirthday.setCellValueFactory(new PropertyValueFactory("birthday"));
        
        
       

     this.tableView2.getColumns().addAll(colId, colName, colBirthday);
    }
    
    private void loadPeople1() throws SQLException{
        Connection conn = JdbcUtils.getconn();
        StaffService s = new StaffService(conn);
        
        tableView1.setItems(FXCollections.observableArrayList(s.getStaffsByWords()));
       
        conn.close();

    }
    private void loadPeople2() throws SQLException{
        Connection conn = JdbcUtils.getconn();
        CustomerService s = new CustomerService(conn);
        
        tableView2.setItems(FXCollections.observableArrayList(s.getCustomersByWords()));
       
        conn.close();

    }
}
