/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Receipt;
import com.kiemthu.pojo.Receipt_Detail;
import com.kiemthu.pojo.service.ReceiptService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 *
 * @author acer
 */
public class ReceiptController implements Initializable{
    @FXML private TableView<Receipt> tvReceipt;
    @FXML private TableView<Receipt_Detail> idReDeTail;
    @FXML private TextField tSearchById;
    @FXML private TableColumn<Receipt, Integer> colid;
    @FXML private TableColumn<Receipt, Float> coltotal;
    @FXML private TableColumn<Receipt, Integer> colidstaff;
    @FXML private TableColumn<Receipt, Integer> colidcustomer;
    @FXML private TableColumn<Receipt, Date> colday;
    @FXML private TableColumn<Receipt_Detail, Integer> colReceiptid;
    @FXML private TableColumn<Receipt_Detail, Integer> colProductid;
    @FXML private TableColumn<Receipt_Detail, Integer> colQuantity;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReceiptService  r = new ReceiptService();
        try {
            loadReceipt();

            
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tvReceipt.setRowFactory(obj -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() > 1){
                    try {
                        Receipt k = this.tvReceipt.getSelectionModel().getSelectedItem();
                        String id = String.valueOf(k.getId());
                        ReceiptService p = new ReceiptService();
                        colReceiptid.setCellValueFactory(new PropertyValueFactory<>("receiptid"));
                        colProductid.setCellValueFactory(new PropertyValueFactory<>("productid"));
                        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                        idReDeTail.setItems(FXCollections.observableArrayList(p.SeachByIDreceipt(id)));
                    } catch (SQLException ex) {
                        Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });
    }
    
    
    
    public void SearchByid() throws SQLException{
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colidstaff.setCellValueFactory(new PropertyValueFactory<>("staff_id"));
        colidcustomer.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colday.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        ReceiptService p = new ReceiptService(); 
        tvReceipt.setItems( FXCollections.observableArrayList(p.SearchReceiptById(tSearchById.getText())));
    }
    
    public void loadReceipt() throws SQLException{
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colidstaff.setCellValueFactory(new PropertyValueFactory<>("staff_id"));
        colidcustomer.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colday.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        ReceiptService p = new ReceiptService();

        colReceiptid.setCellValueFactory(new PropertyValueFactory<>("receiptid"));
        colProductid.setCellValueFactory(new PropertyValueFactory<>("productid"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        idReDeTail.setItems(FXCollections.observableArrayList(p.getReceipt_Detail()));
        tvReceipt.setItems(FXCollections.observableArrayList(p.getReceipt()));
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void addReceipt(ActionEvent event) throws SQLException{
        try {
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addReceipt.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void reHome(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("home.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
