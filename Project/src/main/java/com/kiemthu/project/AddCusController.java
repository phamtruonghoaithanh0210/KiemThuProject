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
import static com.kiemthu.project.AddstaffController.calculateAge;
import static com.kiemthu.project.AddstaffController.checkBirthday;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


/**
 *
 * @author ASUS
 */
public class AddCusController implements Initializable {

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
    private ToggleGroup group = new ToggleGroup();


    public void signUp() throws ParseException, SQLException {
        CustomerService s = new CustomerService (JdbcUtils.getconn());
        if (txtname.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input name");
            alert.showAndWait();
            return;
        }
        if (txtemail.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input email");
            alert.showAndWait();
            return;
        } else {
            if (AddstaffController.checkEmail(txtemail.getText())) {
                //xu lí sau
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Messenge");
                alert.setHeaderText("Results:");
                alert.setContentText("Please enter the correct email");
                alert.showAndWait();
                return;
            }
        }
        if (txtphone.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
        }else {
            if (AddstaffController.checkPhone(txtphone.getText())) {
                //xu lí sau
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Messenge");
                alert.setHeaderText("Results:");
                alert.setContentText("Please enter the correct phone");
                alert.showAndWait();
                return;
            }
        }
        if (txtaddress.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
            return;
        }
        LocalDate parsed ;
        if (idBirthday.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input birthday");
            alert.showAndWait();
            return;

        }else {         
            parsed = idBirthday.getValue();
            if (checkBirthdayCus(parsed)) {
                //xu li
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Messenge");
                alert.setHeaderText("Results:");
                alert.setContentText("Custormer must be 12 years old");
                alert.showAndWait();
                return;
            }
        }
        Customer cusnew = new Customer();
        cusnew.setName(txtname.getText());
        cusnew.setEmail(txtemail.getText());
        // Có lựa chọn
        if (group.getSelectedToggle() != null) {
            RadioButton button = (RadioButton) group.getSelectedToggle();
            if (button.getText().equals("Male")) {
                cusnew.setGender(true);
            } else {
                cusnew.setGender(false);
            }
        }
        cusnew.setPhone(txtphone.getText());
        cusnew.setAddress(txtaddress.getText());
        
        //java.sql.Date dateBithday = new java.sql.Date(parsed.getTime())
        cusnew.setBirthday(Jutil.convertToDatabaseColumn(parsed));
        long millis = System.currentTimeMillis();
        java.sql.Date dateCreated = new java.sql.Date(millis);
        cusnew.setNgaytao(dateCreated);
        cusnew.setUserRole(User.Role.Customer);
        s.addCustormer(cusnew);
        //show thông báo thành công
                Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Messenge");
        alert.setHeaderText("Results:");
        alert.setContentText("custormer added successfully");
        alert.showAndWait();
        loadClear();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdMale.setToggleGroup(group);
        rdMale.setSelected(true);
        rdfemale.setToggleGroup(group);
        idBirthday.setShowWeekNumbers(true);
        idBirthday.setConverter(Jutil.converter);
        idBirthday.setPromptText("dd-MM-yyyy");
    }

    public void loadClear() {
        txtname.setText("");
        txtaddress.setText("");
        txtemail.setText("");
        txtphone.setText("");
        idBirthday.setValue(null);
    }
    public static boolean checkBirthdayCus(LocalDate birthDate) {
        if (calculateAge(birthDate) >= 12) {
            return true;
        } else {
            return false;
        }
    }

}
