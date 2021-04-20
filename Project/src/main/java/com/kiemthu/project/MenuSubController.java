/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import java.io.File;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author ASUS
 */
public class MenuSubController {
    @FXML
     private BorderPane borderPane;
    @FXML
    public void handToSatffManeger(ActionEvent event) throws Exception {
        this.getPhPane("staffmaneger");
    }
    @FXML
    public void handToCusManeger(ActionEvent event) throws Exception {
        this.getPhPane("customermaneger");
    }
    public void getPhPane(String filename) {
       Pane root = null;
        try {
            URL url = new File("./src/main/resources/com/kiemthu/project/"+filename+".fxml").toURI().toURL();
            root = FXMLLoader.load(url);

        } catch (Exception e) {
            System.out.println("khong tim thấy" + filename + "kiểm ra lại" + e);
        }
        borderPane.setCenter(root);
    }
}
