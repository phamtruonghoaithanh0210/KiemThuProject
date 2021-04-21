/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Category;
import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.service.CategoryService;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.ProductService;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 *
 * @author Admin
 */
public class ProductController implements Initializable{
    @FXML private TableView<Product> tbProducts;
    @FXML private TextField txtSearchByName;
    @FXML private TextField txtSearchByPriceFrom;
    @FXML private TextField txtSearchByPriceTo;
    @FXML private ComboBox<Category> cbCates;
    @FXML private Pane paneSearch;
    @FXML private Pane paneAdd;
    @FXML private Pane paneUpdate;
    @FXML private Pane paneDelete;
    @FXML private Pane paneSee;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       loadColumns();
         try {
            loadProducts("");
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void searchHandle (ActionEvent evt){
        paneSearch.setVisible(true);
        paneAdd.setVisible(false);
        paneUpdate.setVisible(false);
        paneDelete.setVisible(false);
        paneSee.setVisible(false);
    }
    public void addHandle(ActionEvent evt){
        paneAdd.setVisible(true);
        paneSearch.setVisible(false);
        paneUpdate.setVisible(false);
        paneDelete.setVisible(false);
        paneSee.setVisible(false);
    }
    public void updateHandle(ActionEvent evt){
        paneUpdate.setVisible(true);
        paneAdd.setVisible(false);
        paneSearch.setVisible(false);
        paneDelete.setVisible(false);
         paneSee.setVisible(false);
    }
    public void deleteHandle(ActionEvent evt){
        paneDelete.setVisible(true);
        paneUpdate.setVisible(false);
        paneAdd.setVisible(false);
        paneSearch.setVisible(false);
        paneSee.setVisible(false);
    }
    public void seeHandle (ActionEvent evt){
        paneSee.setVisible(true);
        paneDelete.setVisible(false);
        paneUpdate.setVisible(false);
        paneAdd.setVisible(false);
        paneSearch.setVisible(false);
        
    }
     private void loadColumns(){
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Name");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colPrice = new TableColumn("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        
        TableColumn colDes = new TableColumn("Description");
        colDes.setCellValueFactory(new PropertyValueFactory("description"));
        
        TableColumn colIdCat = new TableColumn("Categoryid");
        colIdCat.setCellValueFactory(new PropertyValueFactory("categoryid"));
       
        TableColumn colQuantity = new TableColumn("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
       
        
                
                
        this.tbProducts.getColumns().addAll(colId, colName, colPrice,colDes,colIdCat,colQuantity);
    }
    
    private void loadProducts(String kw) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        ProductService s = new ProductService(conn);
        tbProducts.setItems(FXCollections.observableArrayList(s.searchByName(kw)));
        conn.close();

    }
}
