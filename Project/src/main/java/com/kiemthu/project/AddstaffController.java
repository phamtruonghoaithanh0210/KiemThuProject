/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
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
    private TextField txtpassword;
    @FXML
    private TextField txtcomfirmpassword;
    @FXML
    private Text txtMess;
    @FXML
    private RadioButton rdMale;
    @FXML
    private RadioButton rdfemale;
    @FXML
    private DatePicker idBirthday;
    private ToggleGroup group = new ToggleGroup();

    public void signUp() {
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
        }        
        if (idBirthday.getValue()==null) {
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
        }
        if (txtpassword.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
        }
        if (txtcomfirmpassword.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Messenge");
            alert.setHeaderText("Results:");
            alert.setContentText("please input phone");
            alert.showAndWait();
        }
        

        ToggleGroup group = new ToggleGroup();
// Radio 1: Male
        rdMale.setToggleGroup(group);
        rdMale.setSelected(true);
// Radio 3: Female.
        rdfemale.setToggleGroup(group);
        System.out.println("com.kiemthu.project.AddstaffController.signUp()");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
// Radio 1: Male
        rdMale.setToggleGroup(group);
        rdMale.setSelected(true);
// Radio 3: Female.
        rdfemale.setToggleGroup(group);
        idBirthday = new DatePicker();
        idBirthday.setValue(LocalDate.of(2016, 7, 25));
        idBirthday.setShowWeekNumbers(true);
        idBirthday.setConverter(converter);
        idBirthday.setPromptText("dd-MM-yyyy");
    }
    // Converter
    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter
                = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
}
