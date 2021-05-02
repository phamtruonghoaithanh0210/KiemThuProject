/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.Receipt;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.Utils;
import com.kiemthu.pojo.service.CustomerService;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.ProductService;
import com.kiemthu.pojo.service.ReceiptService;
import com.kiemthu.pojo.service.StaffService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author acer
 */
public class AddReceiptController implements Initializable  {
    
    @FXML private Pane paneAddP;
    @FXML private Pane PaneAddReceipt;
    @FXML private Button btnRe;
    @FXML private ComboBox<Staff> cbStaff;
    @FXML private ComboBox<Customer> cbEmployee;
    @FXML private ComboBox<Product> cbProduct;
    @FXML private TextField txtSoLuong;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            paneAddP.setVisible(false);
            btnRe.setVisible(false);
            StaffService s = new StaffService(JdbcUtils.getconn());
            CustomerService c = new CustomerService(JdbcUtils.getconn());
            ProductService p = new ProductService(JdbcUtils.getconn());
            this.cbStaff.setItems(FXCollections.observableList(s.getStaffs()));
            this.cbEmployee.setItems(FXCollections.observableList(c.getCustomer()));
            cbProduct.setItems(FXCollections.observableList(p.getPros()));
            txtSoLuong.setText("0");
        } catch (SQLException ex) {
            Logger.getLogger(AddReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createReceipt() throws SQLException{
        try {
            StaffService s = new StaffService(JdbcUtils.getconn());
            CustomerService c = new CustomerService(JdbcUtils.getconn());
            ReceiptService r = new ReceiptService();
            Date d = Date.valueOf(LocalDate.now());
            Receipt re = new Receipt();
            re.setCreateDate(d);
            re.setStaff_id(cbStaff.getSelectionModel().getSelectedItem().getIduser());
            re.setCustomer_id(cbEmployee.getSelectionModel().getSelectedItem().getIduser());
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            
            if (r.addReceipt(re)){
                a.setContentText("ADD SUCCESSFULL RECEIPT, !!!"); 
                paneAddP.setVisible(true);
                btnRe.setVisible(true);
                PaneAddReceipt.setVisible(false);
            }
            else 
                a.setContentText("FAILED!!!");
            a.show();

        } catch (SQLException ex) {
            Logger.getLogger(AddReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addPIntoReceipt (){
        try {
            ReceiptService r = new ReceiptService();
            int idproduct = cbProduct.getSelectionModel().getSelectedItem().getId();
            int quantity = Integer.parseInt(txtSoLuong.getText());
            
            if(quantity <= 0 || txtSoLuong.getText().equals("")){
                Utils.getBox("QUANTITY NOT NULL !!!", Alert.AlertType.ERROR).show();
            }
            else if(r.addReceipt_Detail(idproduct, quantity)){
               Utils.getBox("ADD  SUCCESSFULL!!!", Alert.AlertType.INFORMATION).show();
                txtSoLuong.setText("0");
            }
            else{
                Utils.getBox("ADD FAILURE!!!", Alert.AlertType.ERROR).show();
                txtSoLuong.setText("0");            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void UpdateTotal(ActionEvent event) throws SQLException, IOException{
        ReceiptService r = new ReceiptService();
        r.uppdateReceiptToTal();
        root = FXMLLoader.load(getClass().getResource("salephone.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
    public void ReCreateReceipt(ActionEvent event) throws IOException{
        try {
            ReceiptService r = new ReceiptService();
            r.deleteReceipt();
            Utils.getBox("RENEW RECEIPT SUCCESSFULL !!!", Alert.AlertType.INFORMATION).show();
            paneAddP.setVisible(false);
            btnRe.setVisible(false);
            PaneAddReceipt.setVisible(true);
            

                    
        } catch (SQLException ex) {
            Logger.getLogger(AddReceiptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
