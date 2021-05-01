package com.kiemthu.project;

import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.StaffService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.Event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
    
    //gang biên công cái để tất cả đều dùng được
    public static Staff stafflogin = new Staff();
    
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btLogin;
    //khai báo biến đên nếu 3 lần thì k cho nhập
    private int count = 0;
    @FXML
    private Text txtMessenge;

    @FXML
    private void switchToHome(ActionEvent event) throws Exception, IOException, SQLException, InterruptedException {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if (username.equals("") || password.equals("")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Enter your username password");
            alert.setContentText("www.PHONEIT.com");
            alert.showAndWait();
        } else {
            Connection conn = JdbcUtils.getconn();
            StaffService s = new StaffService(conn);
            stafflogin = s.checkLogin(username, password);
            if (stafflogin== null) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("wrong password or username !!!");
                alert.setContentText("www.PHONEIT.com");
                alert.showAndWait();
                count++;
                System.out.println(count);
                if (count == 3) {
                    txtUsername.setDisable(true);
                    txtPassword.setDisable(true);
                    btLogin.setDisable(true);
                    txtMessenge.setText("Enter wrong more than 3 times, wait for 60s");
                    Thread countDownThread = new Thread() {
                        @Override
                        public void run() {
                            int count = 60;
                            for (int i = count; i > 0; i--) {
                                txtMessenge.setText("Enter incorrectly more than 3 times, wait 60 seconds : " + String.valueOf(i));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            chayTesst();
                        }
                    };
                    countDownThread.start();
                    count = 0;
                }
                txtMessenge.setText("");
            } else {

                this.switchToScene1(event);
            }
        }

    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("homeit.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void chayTesst() {
        txtUsername.setDisable(false);
        txtPassword.setDisable(false);
        btLogin.setDisable(false);
        txtMessenge.setText("");

    }

}
