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
        this.getPhPane("staffmanagement");
    }
    @FXML
    public void handToCusManeger(ActionEvent event) throws Exception {
        this.getPhPane("cusmanagement");
    }
    @FXML
    public void handToProManeger(ActionEvent event) throws Exception {
        this.getPhPane("productmanagement");
    }
    @FXML
    public void handToSalePhone(ActionEvent event) throws Exception {
        this.getPhPane("salephone");
    }
    @FXML
    public void handToChart(ActionEvent event) throws Exception {
        this.getPhPane("chart");
    }
    @FXML
    public void handToBirthdayList(ActionEvent event) throws Exception {
        this.getPhPane("listbirthday");
    }
    public void getPhPane(String filename) {
       Pane root = null;
        try {
            URL url = new File("./src/main/resources/com/kiemthu/project/"+filename+".fxml").toURI().toURL();
            root = FXMLLoader.load(url);

        } catch (Exception e) {
            System.out.println("không tìm thấy" + filename + "kiểm ra lại" + e);
        }
        borderPane.setCenter(root);
    }
}
