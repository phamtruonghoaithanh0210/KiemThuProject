/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.User;
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
        tablestaff.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    changeStaffDetail();
                    paneDetail.setVisible(true);
                }
            }
        });
        loadData();

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

    //showw bảng detail
    public void changeStaffDetail() {
        Staff Staffselected = tablestaff.getSelectionModel().getSelectedItem();
        txtname.setText(Staffselected.getName());
        txtaddress.setText(Staffselected.getAddress());
        txtemail.setText(Staffselected.getEmail());
        txtphone.setText(Staffselected.getPhone());
        idBirthday.setValue(Jutil.convertToEntityAttribute(Staffselected.getBirthday()));
        if (Staffselected.isGender()) {
            rdMale.setSelected(true);
        } else {
            rdfemale.setSelected(true);
        }

    }
    //showw bảng detail

    public void updateTable() throws SQLException {
        Staff Staffselected = tablestaff.getSelectionModel().getSelectedItem();
        Staffselected.setName(txtname.getText());
        Staffselected.setEmail(txtemail.getText());
        if (group.getSelectedToggle() != null) {
            RadioButton button = (RadioButton) group.getSelectedToggle();
            if (button.getText().equals("Male")) {
                Staffselected.setGender(true);
            } else {
                Staffselected.setGender(false);
            }
        }
        Staffselected.setPhone(txtphone.getText());
        Staffselected.setAddress(txtaddress.getText());
        LocalDate parsed = idBirthday.getValue();
        Staffselected.setBirthday(Jutil.convertToDatabaseColumn(parsed));
        StaffService s = new StaffService(JdbcUtils.getconn());
        System.out.println(s.UpdateStaff(Staffselected));
        loadData();
    }

    //delete
    public void deleteStaff() throws SQLException {
        Staff Staffselected = tablestaff.getSelectionModel().getSelectedItem();
        StaffService s = new StaffService(JdbcUtils.getconn());
        s.deteteStaffByID(Staffselected.getIduser());
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
        colusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        Connection conn;
        try {
            conn = JdbcUtils.getconn();
            StaffService s = new StaffService(conn);
            ObservableList<Staff> list = FXCollections.observableArrayList(s.getStaffs());
            tablestaff.setItems(list);
            FilteredList<Staff> filteredData = new FilteredList<>(list, b -> true);

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
    }

}
