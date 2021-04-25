/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Category;
import com.kiemthu.pojo.Product;
import com.kiemthu.pojo.Utils;
import com.kiemthu.pojo.service.CategoryService;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.ProductService;
import java.io.File;
import java.math.BigDecimal;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML private ComboBox<Category> cbCatesAdd;
    @FXML private ComboBox<Category> cbCatesUpdate;
    @FXML private ComboBox<Category> cbCatesSee;
    @FXML private ComboBox<Product> cbProUpdate;
    @FXML private TextField txtNameAdd, txtPriceAdd,txtQuanAdd,txtDesAdd;
    @FXML private TextField txtNameUpdate, txtPriceUpdate,txtQuanUpdate,txtDesUpdate;
    @FXML private Label lbIdSee, lbNameSee, lbDesSee, lbPriceSee, lbQuantitySee;
    @FXML private Pane paneSearch;
    @FXML private Pane paneAdd;
    @FXML private Pane paneUpdate;
    @FXML private Pane paneSee;
    @FXML private Label lbUpdate;
    @FXML private ImageView imageView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = JdbcUtils.getconn();
            CategoryService s = new CategoryService();
                    try {
                        cbCatesAdd.setItems(FXCollections.observableList(s.getCates()));
                      
                        cbCatesUpdate.setItems(FXCollections.observableList(s.getCates()));
                        cbCatesSee.setItems(FXCollections.observableList(s.getCates()));
                    
                      
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    loadColumns();
                    try {
                        loadProducts("");
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
              this.tbProducts.setRowFactory(obj -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(e -> {
                try {
                    Product p =  this.tbProducts.getSelectionModel().getSelectedItem();
                    txtNameUpdate.setText(p.getName());
                    txtPriceUpdate.setText(p.getPrice().toString());
                    txtQuanUpdate.setText(String.valueOf(p.getQuantity()));
                    txtDesUpdate.setText(p.getDescription());
                    lbUpdate.setText(String.valueOf(p.getId()));
                    
                    CategoryService cat = new CategoryService();
                    cbCatesUpdate.getSelectionModel().select(cat.getCategoryById(p.getCategoryid()));
                    
                  
                    
                    lbNameSee.setText(p.getName());
                    lbPriceSee.setText(p.getPrice().toString());
                    lbQuantitySee.setText(String.valueOf(p.getQuantity()));
                    lbDesSee.setText(p.getDescription());
                    lbIdSee.setText(String.valueOf(p.getId()));
                    File file = new File(p.getDescription());
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                    cbCatesSee.getSelectionModel().select(cat.getCategoryById(p.getCategoryid()));
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });           
                     
                    
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       

    }
    //Tìm kiếm theo tên
    public void searchByName(ActionEvent evt){
         try {
            Connection conn = JdbcUtils.getconn();
            ProductService p = new ProductService(conn);
            tbProducts.setItems(FXCollections.observableArrayList(
                    p.searchByName(txtSearchByName.getText())));
            txtSearchByName.setText("");
            txtSearchByPriceFrom.setText("");
            txtSearchByPriceTo.setText("");
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    //Tìm kiếm theo giá
    public void searchByPrice(ActionEvent evt){
        try {
            Connection conn = JdbcUtils.getconn();
            ProductService p = new ProductService(conn);
            tbProducts.setItems(FXCollections.observableArrayList(
                    p.searchByPrice(new BigDecimal(txtSearchByPriceFrom.getText()), 
                    new BigDecimal(txtSearchByPriceTo.getText()))));
            txtSearchByName.setText("");
            txtSearchByPriceFrom.setText("");
            txtSearchByPriceTo.setText("");
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    //Button chuyển giao diện tìm kiếm sản phẩm
    public void searchHandle (ActionEvent evt){
        try {
            loadProducts("");
            paneSearch.setVisible(true);
            paneAdd.setVisible(false);
            paneUpdate.setVisible(false);
            paneSee.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Button chuyển giao diện thêm sản phẩm
    public void addHandle(ActionEvent evt){
        try {
             clearContent(evt);
            loadProducts("");
            paneAdd.setVisible(true);
            paneSearch.setVisible(false);
            paneUpdate.setVisible(false);
            paneSee.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //Button thêm sản phẩm
    public void addProduct(ActionEvent evt){
        try {
            Connection conn = JdbcUtils.getconn();
            CategoryService s = new CategoryService();
            ProductService pro = new ProductService(conn);
            Product p = new Product();
            p.setName(txtNameAdd.getText());
            p.setPrice(new BigDecimal(txtPriceAdd.getText()));
            p.setCategoryid(cbCatesAdd.getSelectionModel().getSelectedItem().getId());
            p.setQuantity(Integer.parseInt(txtQuanAdd.getText()));
            p.setDescription(txtDesAdd.getText());
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            if (pro.addProduct(p)==true){
                a.setContentText("ADD SUCCESSFULL PRODUCT!!!");
                loadProducts("");
                 clearContent(evt);
               
            }
            else 
                a.setContentText("FAILED!!!");
            a.show();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        //Button clearcontent sản phẩm
    public void clearContent(ActionEvent evt){
        CategoryService s = new CategoryService();
        try {
          cbCatesAdd.setItems(FXCollections.observableList(s.getCates()));
          txtNameAdd.setText("");
          txtPriceAdd.setText("");
          txtQuanAdd.setText("");
          txtDesAdd.setText("");
          
          
          txtNameUpdate.setText("");txtPriceUpdate.setText("");txtQuanUpdate.setText("");txtDesUpdate.setText("");lbUpdate.setText("");
          cbCatesUpdate.setItems(FXCollections.observableList(s.getCates()));
          
          
          lbIdSee.setText(""); lbNameSee.setText(""); lbDesSee.setText(""); lbPriceSee.setText(""); lbQuantitySee.setText("");
          cbCatesSee.setItems(FXCollections.observableList(s.getCates()));
          
           File file = new File("");
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
          loadProducts("");
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Button chuyển giao diện cập nhật sản phẩm
    public void updateHandle(ActionEvent evt){
        try {
            loadProducts("");
            clearContent(evt);
            paneUpdate.setVisible(true);
            paneAdd.setVisible(false);
            paneSearch.setVisible(false);
            paneSee.setVisible(false);
  
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void updateProduct(ActionEvent evt) throws SQLException{
        CategoryService c = new CategoryService();
        Product p = this.tbProducts.getSelectionModel().getSelectedItem();
        
        if (txtNameUpdate.getText() == "")
            Utils.getBox("Product name mustn't null", Alert.AlertType.ERROR).show();
        else if ((txtPriceUpdate.getText()) == "")
            Utils.getBox("Product price mustn't null", Alert.AlertType.ERROR).show();
        else 
        {
            p.setName(txtNameUpdate.getText());
            p.setDescription(txtDesUpdate.getText());
            p.setPrice(new BigDecimal(txtPriceUpdate.getText()));
            p.setQuantity(Integer.parseInt(txtQuanUpdate.getText()));
            p.setCategoryid(this.cbCatesUpdate.getSelectionModel().getSelectedItem().getId());
            Connection conn = JdbcUtils.getconn();
            ProductService pro = new ProductService(conn);
            if (pro.updateProduct(p) == true){
                Utils.getBox("UPDATE PRODUCT SUCCESSFULL!!!", Alert.AlertType.INFORMATION).show();
                loadProducts("");
                clearContent(evt);
            }
            else 
                Utils.getBox("UPDATE PRODUCT FAILED!!!", Alert.AlertType.ERROR).show();
            conn.close();
        }
        
    }
    

//    public void updateProduct(ActionEvent evt){
//        cbCatesUpdate.valueProperty().addListener((obj)->{
//            try {
//                Connection conn = JdbcUtils.getconn();
//                ProductService p = new ProductService(conn);
//                Product pro = p.getProductById(Integer.parseInt(cbCatesUpdate.getId()));
//                txtNameUpdate.setText(pro.getName());
//                txtPriceUpdate.setText(String.valueOf(pro.getPrice()));
//                txtQuanUpdate.setText(String.valueOf(pro.getQuantity()));
//                txtDesUpdate.setText(pro.getDescription());
//                CategoryService cat = new CategoryService();
//                cbCatesUpdate.getSelectionModel().select(cat.getCategoryById(pro.getCategoryid()));
//            } catch (SQLException ex) {
//                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//        }
//        );
//    }
    

    
    public void seeHandle (ActionEvent evt){
        try {
            loadProducts("");
             clearContent(evt);
            paneSee.setVisible(true);
            paneUpdate.setVisible(false);
            paneAdd.setVisible(false);
            paneSearch.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
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
