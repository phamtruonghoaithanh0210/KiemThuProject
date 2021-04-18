/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.StaffService;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 *
 * @author ASUS
 */
public class StaffManegerController implements Initializable {

    @FXML
    private TableView<Staff> tablestaff;
    @FXML
    private TableColumn<Staff,Integer> colid;
    @FXML
    private TableColumn<Staff,String> colname;
    @FXML
    private TableColumn<Staff,String>  colemail;
    @FXML
    private TableColumn<Staff,Boolean>  colgender;
    @FXML
    private TableColumn<Staff,Date>  colbrithday;
    @FXML
    private TableColumn<Staff,Date>  colcreatedate;
    @FXML
    private TableColumn<Staff,String>  colphone;
    @FXML
    private TableColumn<Staff,String>  coladdress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory<Staff,Integer> ("iduser"));
        colname.setCellValueFactory(new PropertyValueFactory<Staff,String> ("name"));
        colemail.setCellValueFactory(new PropertyValueFactory<Staff,String> ("email"));
        colgender.setCellValueFactory(new PropertyValueFactory<Staff,Boolean> ("gender"));
        colbrithday.setCellValueFactory(new PropertyValueFactory<Staff,Date>("birthday"));
        colcreatedate.setCellValueFactory(new PropertyValueFactory<Staff,Date>("ngaytao"));
        colphone.setCellValueFactory(new PropertyValueFactory<Staff,String> ("phone"));
        coladdress.setCellValueFactory(new PropertyValueFactory<Staff,String> ("address"));
         Connection conn;
        try {
            conn = JdbcUtils.getconn();
            StaffService s = new StaffService(conn);
            ObservableList<Staff> list = FXCollections.observableArrayList(s.getStaffs());
            tablestaff.setItems(list);

        } catch (SQLException ex) {
            Logger.getLogger(StaffManegerController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
}
