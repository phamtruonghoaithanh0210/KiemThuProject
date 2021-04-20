package com.kiemthu.project;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    LoginController l = new LoginController();

    @FXML
    private Text txtNameuser;

    @FXML
    private Pane view;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView avatar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtNameuser.setText("Hi! " + LoginController.stafflogin.getName());
       // avatar = new ImageView(getClass().getResource("./src/main/resources/com/kiemthu/project/img/girl.png").toExternalForm());
        
        ActionEvent event = null;
        try {
            handTohome(event);
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handTohome(ActionEvent event) throws Exception {

        this.getPhPane("home");

    }

    @FXML
    public void handToProfile(ActionEvent event) throws Exception {

        this.getPhPane("profile");

    }

    @FXML
    public void handToLogout(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("logout.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        LoginController.stafflogin = null;
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("homeit.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //menu chuyen qua trong home
    public void getPhPane(String filename) {
        Pane root = null;
        try {
            URL url = new File("./src/main/resources/com/kiemthu/project/" + filename + ".fxml").toURI().toURL();
            root = FXMLLoader.load(url);

        } catch (Exception e) {
            System.out.println("khong tim thấy" + filename + "kiểm ra lại" + e);
        }
        borderPane.setCenter(root);
    }


}