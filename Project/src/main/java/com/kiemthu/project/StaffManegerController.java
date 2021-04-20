/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Staff;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author ASUS
 */
public class StaffManegerController implements Initializable {

    @FXML
    private TableView<Staff> tablestaff;
    @FXML
    private TableColumn<Staff, Integer> colid;
    @FXML
    private TableColumn<Staff, String> colname;
    @FXML
    private TableColumn<Staff, String> colemail;
    @FXML
    private TableColumn<Staff, Boolean> colgender;
    @FXML
    private TableColumn<Staff, Date> colbrithday;
    @FXML
    private TableColumn<Staff, Date> colcreatedate;
    @FXML
    private TableColumn<Staff, String> colphone;
    @FXML
    private TableColumn<Staff, String> coladdress;
    @FXML
    private TextField filterField;
    @FXML
    private TableColumn<Staff, String> colusername;

    @FXML
    private TextField txtname;
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txtphone;
    @FXML
    private TextField txtaddress;
    @FXML
    private TextField txtusername;
    @FXML
    private PasswordField txtpassword;
    @FXML
    private PasswordField txtcomfirmpassword;
    @FXML
    private RadioButton rdMale;
    @FXML
    private RadioButton rdfemale;
    @FXML
    private DatePicker idBirthday;
    private ToggleGroup group = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("iduser"));
        colname.setCellValueFactory(new PropertyValueFactory<Staff, String>("name"));
        colemail.setCellValueFactory(new PropertyValueFactory<Staff, String>("email"));
        colgender.setCellValueFactory(new PropertyValueFactory<Staff, Boolean>("gender"));
        colbrithday.setCellValueFactory(new PropertyValueFactory<Staff, Date>("birthday"));
        colcreatedate.setCellValueFactory(new PropertyValueFactory<Staff, Date>("ngaytao"));
        colphone.setCellValueFactory(new PropertyValueFactory<Staff, String>("phone"));
        coladdress.setCellValueFactory(new PropertyValueFactory<Staff, String>("address"));
        colusername.setCellValueFactory(new PropertyValueFactory<Staff, String>("username"));
        Connection conn;
        try {
            conn = JdbcUtils.getconn();
            StaffService s = new StaffService(conn);
            ObservableList<Staff> list = FXCollections.observableArrayList(s.getStaffs());
            tablestaff.setItems(list);
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Staff> filteredData = new FilteredList<>(list, b -> true);

            // 2. Set the filter Predicate whenever the filter changes.
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
            SortedList<Staff> sortedData = new SortedList<>(filteredData);
            // 4. liên kết với bản so sánh
            sortedData.comparatorProperty().bind(tablestaff.comparatorProperty());
            // 5. thêm và sắp xêm vào bảng
            tablestaff.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(StaffManegerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablestaff.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    changeStaffDetail();
                }
            }
        });

    }

    public void addStaff() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addstaff.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LocalDate convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date)
                .map(Date::toLocalDate)
                .orElse(null);
    }

    public Date convertToDatabaseColumn(LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(Date::valueOf)
                .orElse(null);
    }
    //showw bảng detail
    public void changeStaffDetail() {
        Staff Staffselected = tablestaff.getSelectionModel().getSelectedItem();
        txtname.setText(Staffselected.getName());
        txtaddress.setText(Staffselected.getAddress());
        txtemail.setText(Staffselected.getEmail());
        txtphone.setText(Staffselected.getPhone());
        idBirthday.setValue(this.convertToEntityAttribute(Staffselected.getBirthday()));
        if (Staffselected.isGender()) {
            rdMale.setSelected(true);
        } else {
            rdfemale.setSelected(true);
        }

    }
    
}
