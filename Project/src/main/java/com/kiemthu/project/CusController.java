/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.User;
import com.kiemthu.pojo.service.CustomerService;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.StaffService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author ASUS
 */
public class CusController implements Initializable {

    @FXML
    private TableView<Customer> tableCustomer;
    @FXML
    private TableColumn<Customer, Integer> colid;
    @FXML
    private TableColumn<Customer, String> colname;
    @FXML
    private TableColumn<Customer, String> colemail;
    @FXML
    private TableColumn<Customer, Boolean> colgender;
    @FXML
    private TableColumn<Customer, Date> colbrithday;
    @FXML
    private TableColumn<Customer, Date> colcreatedate;
    @FXML
    private TableColumn<Customer, String> colphone;
    @FXML
    private TableColumn<Customer, String> coladdress;
    @FXML
    private TextField filterField;

    @FXML
    private TextField txtname;
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txtphone;
    @FXML
    private TextField txtaddress;
    @FXML
    private RadioButton rdMale;
    @FXML
    private RadioButton rdfemale;
    @FXML
    private DatePicker idBirthday;
    @FXML
    private Pane paneDetail;
    private ToggleGroup group = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneDetail.setVisible(false);
        rdMale.setToggleGroup(group);
        rdMale.setSelected(true);
        rdfemale.setToggleGroup(group);
        idBirthday.setShowWeekNumbers(true);
        idBirthday.setConverter(Jutil.converter);
        idBirthday.setPromptText("dd-MM-yyyy");
        tableCustomer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    changeCusDetail();
                    paneDetail.setVisible(true);
                }
            }
        });
        loadData();

    }

    public void addCus() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addcus.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //showw bảng detail
    public void changeCusDetail() {
        Customer customerselected = tableCustomer.getSelectionModel().getSelectedItem();
        txtname.setText(customerselected.getName());
        txtaddress.setText(customerselected.getAddress());
        txtemail.setText(customerselected.getEmail());
        txtphone.setText(customerselected.getPhone());
        idBirthday.setValue(Jutil.convertToEntityAttribute(customerselected.getBirthday()));
        if (customerselected.isGender()) {
            rdMale.setSelected(true);
        } else {
            rdfemale.setSelected(true);
        }

    }
    //showw bảng detail

    public void updateCus() throws SQLException {
        System.err.println("oke");
        Customer customerselected = tableCustomer.getSelectionModel().getSelectedItem();
        customerselected.setName(txtname.getText());
        customerselected.setEmail(txtemail.getText());
        if (group.getSelectedToggle() != null) {
            RadioButton button = (RadioButton) group.getSelectedToggle();
            if (button.getText().equals("Male")) {
                customerselected.setGender(true);
            } else {
                customerselected.setGender(false);
            }
        }
        customerselected.setPhone(txtphone.getText());
        customerselected.setAddress(txtaddress.getText());
        LocalDate parsed = idBirthday.getValue();
        customerselected.setBirthday(Jutil.convertToDatabaseColumn(parsed));
        CustomerService s = new CustomerService(JdbcUtils.getconn());
        s.updateCustomer(customerselected);
        loadData();
    }

    //delete
    public void deleteCus() throws SQLException {
        Customer  customerselected = tableCustomer.getSelectionModel().getSelectedItem();
       CustomerService s = new CustomerService(JdbcUtils.getconn());
        s.deteteCustomerByID(customerselected.getIduser());
        txtname.setText("");
        txtaddress.setText("");
        txtemail.setText("");
        txtphone.setText("");
        idBirthday.setValue(LocalDate.now());
        rdMale.setSelected(true);
        loadData();
    }
    //upload

    /**
     *
     */
    public void loadData() {
        colid.setCellValueFactory(new PropertyValueFactory<>("iduser"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colbrithday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colcreatedate.setCellValueFactory(new PropertyValueFactory<>("ngaytao"));
        colphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        Connection conn;
        try {
            conn = JdbcUtils.getconn();
            CustomerService s = new CustomerService (conn);
            ObservableList<Customer> list = FXCollections.observableArrayList(s.getCustomer());
            tableCustomer.setItems(list);
            FilteredList<Customer> filteredData = new FilteredList<>(list, b -> true);

            filterField.textProperty().addListener((var observable, var oldValue, var newValue) -> {
                filteredData.setPredicate((var employee) -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (employee.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (employee.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (String.valueOf(employee.getAddress()).indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(employee.getPhone()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(employee.getBirthday()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(employee.getNgaytao()).contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return String.valueOf(employee.getIduser()).contains(lowerCaseFilter); // Does not match.
                    }
                });
            });

            // 3. sắp xếp theo cái dã lọc
            SortedList<Customer> sortedData = new SortedList<>(filteredData);
            // 4. liên kết với bản so sánh
            sortedData.comparatorProperty().bind(tableCustomer.comparatorProperty());
            // 5. thêm và sắp xêm vào bảng
            tableCustomer.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(StaffManegerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
