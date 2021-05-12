/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.User;
import com.kiemthu.pojo.Utils;
import com.kiemthu.pojo.service.CategoryService;
import com.kiemthu.pojo.service.CustomerService;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.ProductService;
import com.kiemthu.pojo.service.StaffService;
import com.kiemthu.pojo.service.StatisticalService;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class BirthdayController implements Initializable {

     @FXML private TableView<User> tableView1;
     @FXML private TableView<User> tableView2;
     @FXML private ComboBox cbMonth;
     @FXML private Label lbBirthday;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             loadColumns1();
             loadColumns2();
             
             ObservableList<String> list = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12");
             
             cbMonth.setItems(list);
            
            cbMonth.valueProperty().addListener(obj ->{
                 try {
                     loadPeople1(cbMonth.getSelectionModel().getSelectedIndex()+1);
                     loadPeople2(cbMonth.getSelectionModel().getSelectedIndex()+1);
                     if(tableView1.getItems().size() <= 0 && tableView2.getItems().size() <= 0)
                         Utils.getBox("Không tìm thấy nhân viên và khách hàng!!!","", Alert.AlertType.ERROR).show();
                 } catch (SQLException ex) {
                     Logger.getLogger(BirthdayController.class.getName()).log(Level.SEVERE, null, ex);
                 }
           });
             
        
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
    
    private void loadPeople1(int month) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        StatisticalService s1 = new StatisticalService();
             
        tableView2.setItems(FXCollections.observableArrayList(s1.birthdayCustomer(month)));
       
        
        conn.close();

    }
    private void loadPeople2(int month) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        StatisticalService s1 = new StatisticalService();
             
        tableView1.setItems(FXCollections.observableArrayList(s1.birthdayStaff(month)));
       
        conn.close();

    }
}
