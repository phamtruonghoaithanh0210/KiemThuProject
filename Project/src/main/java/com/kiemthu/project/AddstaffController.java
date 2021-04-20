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
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 *
 * @author ASUS
 */
public class AddstaffController implements Initializable {

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


    public void signUp() throws ParseException, SQLException {
        StaffService s = new StaffService(JdbcUtils.getconn());
        if (txtname.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input name");
            alert.showAndWait();
            return;
        }
        if (txtemail.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input email");
            alert.showAndWait();
            return;
        }
        if (txtphone.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
        }
        if (txtaddress.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
            return;
        }
        if (idBirthday.getValue() == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input birtday");
            alert.showAndWait();
        }
        if (txtusername.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
            return;
        } else {
            if (this.checkusername(txtusername.getText())) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Messenge");
                alert.setHeaderText("Results:");
                alert.setContentText("usere bị trùng");
                alert.showAndWait();
                return;

            }

        }
        if (txtpassword.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
            return;
        } else {
            if (this.checkP(txtpassword.getText())) {
                //xu lí sau
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Messenge");
                alert.setHeaderText("Results:");
                alert.setContentText("Password must have 8 char, 1Up,1 low,1 number");
                alert.showAndWait();
                return;
            }
        }
        if (txtcomfirmpassword.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
            return;
        } else {
            if (txtcomfirmpassword.getText().equals(txtpassword.getText()) == false) {
                System.out.println(txtcomfirmpassword.getText());
                System.err.println(txtpassword.getText());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Messenge");
                alert.setHeaderText("Results:");
                alert.setContentText("comfirmpassword not like password");
                alert.showAndWait();
                return;
            }
        }
        if (idBirthday.getValue() == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input birthday");
            alert.showAndWait();
            return;

        }
        Staff staffnew = new Staff();
        staffnew.setName(txtname.getText());
        staffnew.setEmail(txtemail.getText());
        // Có lựa chọn
        if (group.getSelectedToggle() != null) {
            RadioButton button = (RadioButton) group.getSelectedToggle();
            if (button.getText().equals("Male")) {
                staffnew.setGender(true);
            } else {
                staffnew.setGender(false);
            }
        }
        staffnew.setPhone(txtphone.getText());
        staffnew.setAddress(txtaddress.getText());
        LocalDate parsed = idBirthday.getValue();
        //java.sql.Date dateBithday = new java.sql.Date(parsed.getTime())
        staffnew.setBirthday(this.convertToDatabaseColumn(parsed));
        long millis = System.currentTimeMillis();
        java.sql.Date dateCreated = new java.sql.Date(millis);
        staffnew.setNgaytao(dateCreated);
        staffnew.setUserRole(User.Role.Staff);
        System.out.println(staffnew.getNgaytao());
        staffnew.setUsername(txtusername.getText());
        staffnew.setPassword(txtpassword.getText());
        s.addStaff(staffnew);

    }
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return Optional.ofNullable(localDate)
          .map(Date::valueOf)
          .orElse(null);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdMale.setToggleGroup(group);
        rdMale.setSelected(true);
        rdfemale.setToggleGroup(group);
        idBirthday.setShowWeekNumbers(true);
        idBirthday.setConverter(converter);
        idBirthday.setPromptText("dd-MM-yyyy");
    }
    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter
                = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };

    public boolean checkP(final String password) {
        if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", password) == true) {
            return true;
        } else {
            return false;
        }
    }

    public void loadClear() {
        txtname.setText("");
        txtaddress.setText("");
        txtcomfirmpassword.setText("");
        txtpassword.setText("");
        txtemail.setText("");
        txtusername.setText("");
        txtphone.setText("");
        idBirthday.setValue(null);
    }

    public boolean checkusername(String username) throws SQLException {
        StaffService s = new StaffService(JdbcUtils.getconn());
        if (s.searchByUsename(username) == null) {
            return false;
        } else {
            return true;
        }

    }

}
