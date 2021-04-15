package com.kiemthu.project;

import com.kiemthu.pojo.service.JdbcUtils;
import com.kiemthu.pojo.service.StaffService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

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
    private void switchToHome() throws IOException, SQLException, InterruptedException {
        //App.setRoot("home");
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if(username.equals("") || password.equals("")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Nhập username password nhé");
            alert.setContentText("www.PHONEIT.com");
            alert.showAndWait();
        } else {
        Connection conn = JdbcUtils.getconn();
        StaffService s = new StaffService(conn);
        if (s.checkLogin(username, password) == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("sai mật khẩu hoặc username!!!");
            alert.setContentText("www.PHONEIT.com");
            alert.showAndWait();
            count++;
            System.out.println(count);
            if (count == 3) {
                txtUsername.setDisable(true);
                txtPassword.setDisable(true);
                btLogin.setDisable(true);
                txtMessenge.setText("Nhập sai quá 3 lần, đợi 60S nha đồ ngốc");
                Thread countDownThread = new Thread() {
                    @Override
                    public void run() {
                        int count = 60;
                        for (int i = count; i > 0; i--) {
                            txtMessenge.setText( "Nhập sai quá 3 lần đợi 60 giây nhé bae giờ: " +String.valueOf(i));
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
            App.setRoot("homeit");
        }
        }

    }

    public void chayTesst() {
        txtUsername.setDisable(false);
        txtPassword.setDisable(false);
        btLogin.setDisable(false);
        txtMessenge.setText("");

    }

}
