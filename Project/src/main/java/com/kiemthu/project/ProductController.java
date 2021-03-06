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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class ProductController implements Initializable{
    List<String> lstFile;
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
    @FXML private AnchorPane anchorpane;
    @FXML private Label lbPathAdd, lbPath;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lstFile = new ArrayList<>();
            lstFile.add("*.jpg");
            lstFile.add("*.png");
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
                    txtPriceUpdate.setText( p.getPrice().toString());
                    txtQuanUpdate.setText(String.valueOf(p.getQuantity()));
                    txtDesUpdate.setText(p.getDescription());
                    lbUpdate.setText(String.valueOf(p.getId()));
                    lbPath.setText(p.getImage_link());
                    CategoryService cat = new CategoryService();
                    cbCatesUpdate.getSelectionModel().select(cat.getCategoryById(p.getCategoryid()));
                    
                  
                     if(paneSearch.isVisible()==true){
                           paneSearch.setVisible(false);
                           paneSee.setVisible(true);
                     }
                
            
                    lbNameSee.setText(p.getName());
                    lbPriceSee.setText(String.format("%,.0f VN??", p.getPrice()));
                    lbQuantitySee.setText(String.valueOf(p.getQuantity()));
                    lbDesSee.setText(p.getDescription());
                    lbIdSee.setText(String.valueOf(p.getId()));
                    File file = new File(p.getImage_link());
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
    //S???p x???p t??ng d???n theo gi??
    public void increasePrice(ActionEvent evt){
         try {
            Connection conn = JdbcUtils.getconn();
            ProductService p = new ProductService(conn);
            tbProducts.setItems(FXCollections.observableArrayList(
                    p.increases()));
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    //S???p x???p gi???m d???n theo gi??
    public void decreasePrice(ActionEvent evt){
         try {
            Connection conn = JdbcUtils.getconn();
            ProductService p = new ProductService(conn);
            tbProducts.setItems(FXCollections.observableArrayList(
                    p.decreases()));
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    //T??m ki???m theo t??n
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
    //T??m ki???m theo gi??
    public void searchByPrice(ActionEvent evt){
        try {
            Connection conn = JdbcUtils.getconn();
            ProductService p = new ProductService(conn);
            ProductController pd = new ProductController();
            
           txtSearchByPriceFrom.setText(txtSearchByPriceFrom.getText().replaceAll(" ",""));
           txtSearchByPriceTo.setText(txtSearchByPriceTo.getText().replaceAll(" ",""));
            if (pd.isNumeric(txtSearchByPriceTo.getText()) == false ||pd.isNumeric(txtSearchByPriceFrom.getText()) == false 
                ||Integer.parseInt(txtSearchByPriceTo.getText()) < 0 || Integer.parseInt(txtSearchByPriceFrom.getText()) < 0 )
            {
                Utils.getBox("Gi?? s???n ph???m kh??ng h???p l???","CAN'T SEARCH PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else 
            {
                tbProducts.setItems(FXCollections.observableArrayList(
                        p.searchByPrice(new BigDecimal(txtSearchByPriceFrom.getText()), 
                        new BigDecimal(txtSearchByPriceTo.getText()))));
                txtSearchByName.setText("");
                txtSearchByPriceFrom.setText("");
                txtSearchByPriceTo.setText("");
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    //Button chuy???n giao di???n t??m ki???m s???n ph???m
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
    //Button chuy???n giao di???n th??m s???n ph???m
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
    //Button th??m s???n ph???m
    public void addProduct(ActionEvent evt){
        try {
            Connection conn = JdbcUtils.getconn();
            CategoryService s = new CategoryService();
            ProductService pro = new ProductService(conn);
            Product p = new Product();
            
            txtPriceAdd.setText(txtPriceAdd.getText().replaceAll(" ",""));
            txtQuanAdd.setText(txtQuanAdd.getText().replaceAll(" ",""));
            ProductController pd = new ProductController();
            if (txtQuanAdd.getText().isEmpty() == true && txtPriceAdd.getText().isEmpty() == true
                && txtNameAdd.getText().isEmpty() == true && txtDesAdd.getText().isEmpty() == true
                && cbCatesAdd.getSelectionModel().getSelectedIndex() < 0)
            {
                Utils.getBox("Ch??a nh???p d??? li???u","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (pd.isNumeric(txtPriceAdd.getText()) == false)
            {
                Utils.getBox("Gi?? s???n ph???m kh??ng h???p l???","CAN'T ADD PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (pd.isNumeric(txtQuanAdd.getText()) == false )
            {
                Utils.getBox("S??? l?????ng s???n ph???m kh??ng h???p l???","CAN'T ADD PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if(txtNameAdd.getText().replaceAll(" ", "").isEmpty() == true){
                Utils.getBox("T??n s???n ph???m r???ng","CAN'T ADD PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (cbCatesAdd.getSelectionModel().getSelectedIndex() < 0){
                Utils.getBox("Ch??a ch???n danh m???c s???n ph???m.","CAN'T ADD PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (txtPriceAdd.getText().replaceAll(" ", "").isEmpty() == true){
                Utils.getBox("Gi?? s???n ph???m r???ng.","CAN'T ADD PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (txtQuanAdd.getText().replaceAll(" ", "").isEmpty() == true){
                Utils.getBox("S??? l?????ng s???n ph???m r???ng.","CAN'T ADD PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (Integer.parseInt(txtPriceAdd.getText())<=0){
                 Utils.getBox("Gi?? s???n ph???m ph???i l???n h??n 0.","CAN'T ADD PRODUCT", Alert.AlertType.ERROR).show();
                 clearContent(evt);
            }
            else if (Integer.parseInt(txtQuanAdd.getText())<=0){
                 Utils.getBox("S??? l?????ng s???n ph???m ph???i l???n h??n 0.","CAN'T ADD PRODUCT", Alert.AlertType.ERROR).show();
                 clearContent(evt);
            }
            else
            {
               
                p.setPrice(new BigDecimal(txtPriceAdd.getText()));
                p.setName(txtNameAdd.getText().trim());
                p.setCategoryid(cbCatesAdd.getSelectionModel().getSelectedItem().getId());
                p.setQuantity(Integer.parseInt(txtQuanAdd.getText()));
                p.setDescription(txtDesAdd.getText().trim());
                p.setImage_link(lbPathAdd.getText());
                if (pro.addProduct(p)== true){
                    Utils.getBox("Th??m th??nh c??ng s???n ph???m","ADD PRODUCT SUCCESSFUL!!!", Alert.AlertType.INFORMATION).show();
                    loadProducts("");
                    clearContent(evt);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        //Button clearcontent s???n ph???m
    public void clearContent(ActionEvent evt){
        CategoryService s = new CategoryService();
        try {
          cbCatesAdd.setItems(FXCollections.observableList(s.getCates()));
          txtNameAdd.setText("");
          txtPriceAdd.setText("");
          txtQuanAdd.setText("");
          txtDesAdd.setText("");
          lbPath.setText("");
          
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
    //Button chuy???n giao di???n c???p nh???t s???n ph???m
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
        

        txtPriceUpdate.setText(txtPriceUpdate.getText().replaceAll(" ",""));
        txtQuanUpdate.setText(txtQuanUpdate.getText().replaceAll(" ",""));
        ProductController pd = new ProductController();
            if (txtQuanUpdate.getText().isEmpty() == true && txtPriceUpdate.getText().isEmpty() == true
                && txtNameUpdate.getText().isEmpty() == true && txtDesUpdate.getText().isEmpty() == true
                && cbCatesUpdate.getSelectionModel().getSelectedIndex() < 0)
            {
                Utils.getBox("Ch??a nh???p d??? li???u","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (pd.isNumeric(txtPriceUpdate.getText()) == false)
            {
                Utils.getBox("Gi?? s???n ph???m kh??ng h???p l???","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (pd.isNumeric(txtQuanUpdate.getText()) == false )
            {
                Utils.getBox("S??? l?????ng s???n ph???m kh??ng h???p l???","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if(txtNameUpdate.getText().replaceAll(" ", "").isEmpty() == true){
                Utils.getBox("T??n s???n ph???m r???ng","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (txtPriceUpdate.getText().replaceAll(" ", "").isEmpty() == true){
                Utils.getBox("Gi?? s???n ph???m r???ng.","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (txtQuanUpdate.getText().replaceAll(" ", "").isEmpty() == true){
                Utils.getBox("S??? l?????ng s???n ph???m r???ng.","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                clearContent(evt);
            }
            else if (Integer.parseInt(txtPriceUpdate.getText())<=0){
                 Utils.getBox("Gi?? s???n ph???m ph???i l???n h??n 0.","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                 clearContent(evt);
            }
            else if (Integer.parseInt(txtQuanUpdate.getText())<=0){
                 Utils.getBox("S??? l?????ng s???n ph???m ph???i l???n h??n 0.","CAN'T UPDATE PRODUCT", Alert.AlertType.ERROR).show();
                 clearContent(evt);
            }
        else 
        {
            p.setName(txtNameUpdate.getText());
            p.setDescription(txtDesUpdate.getText());
            p.setPrice(new BigDecimal(txtPriceUpdate.getText()));
            p.setQuantity(Integer.parseInt(txtQuanUpdate.getText()));
         
            p.setImage_link(lbPath.getText());
            p.setCategoryid(this.cbCatesUpdate.getSelectionModel().getSelectedItem().getId());
            Connection conn = JdbcUtils.getconn();
            ProductService pro = new ProductService(conn);
            if (pro.updateProduct(p) == true){
                Utils.getBox("C???p nh???t s???n ph???m th??nh c??ng!!!","UPDATE PRODUCT SUCCESSFULL!!!", Alert.AlertType.INFORMATION).show();
                loadProducts("");
                clearContent(evt);
            }
           
            conn.close();
        }
        
    }
    

    

    
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
        
        
        TableColumn colImg = new TableColumn("Image");
        colImg.setCellValueFactory(new PropertyValueFactory("image_link"));

      
        this.tbProducts.getColumns().addAll(colId, colName, colPrice, colDes, colIdCat, colQuantity, colImg);
    }
    private void loadProducts(String kw) throws SQLException{
        Connection conn = JdbcUtils.getconn();
        ProductService s = new ProductService(conn);
        tbProducts.setItems(FXCollections.observableArrayList(s.searchByName(kw)));
        conn.close();

    }
    public static boolean isNumeric(String str) { 
      try {  
        Integer.parseInt(str);  
        return true;
      } catch(NumberFormatException e){  
        return false;  
      }  
    }

    

    public void buttonFileChooser(ActionEvent evt){
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Open File Dialog");
        filechooser.getExtensionFilters().add(new ExtensionFilter("Image Files", lstFile));
        
        File f = filechooser.showOpenDialog(null);
        
        if (f != null)
        {
            lbPathAdd.setText(f.getAbsolutePath());
            lbPath.setText(f.getAbsolutePath());
        }
        
  
    }
}
