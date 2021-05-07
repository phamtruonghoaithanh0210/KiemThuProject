/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.Utils;
import com.kiemthu.pojo.service.StatisticalService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



/**
 *
 * @author Admin
 */
public class ChartController implements Initializable{
     
    @FXML
    private BarChart<?, ?> barChart;
    
    @FXML 
    private TextField txtYear;
    
    @FXML
    private Button btnResult;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             txtYear.textProperty().addListener((obj) -> {
                    
                 btnResult.setDisable(false);
                 if(txtYear.getText().isEmpty()==true ||txtYear.getText().replaceAll(" ", "").isEmpty() == true)
                     btnResult.setDisable(true);
                });

            StatisticalService s = new StatisticalService();
            XYChart.Series series = new XYChart.Series();
            series.getData().removeAll(Collections.singleton(barChart.getData().setAll()));
       
            series.setName("");
            series.getData().add(new XYChart.Data("1",s.totalPriceInMonth(1, 0)));
            series.getData().add(new XYChart.Data("2",s.totalPriceInMonth(2, 0)));
            series.getData().add(new XYChart.Data("3",s.totalPriceInMonth(3, 0)));
            series.getData().add(new XYChart.Data("4",s.totalPriceInMonth(4, 0)));
            series.getData().add(new XYChart.Data("5",s.totalPriceInMonth(5, 0)));
            series.getData().add(new XYChart.Data("6",s.totalPriceInMonth(6, 0)));
            series.getData().add(new XYChart.Data("7",s.totalPriceInMonth(7, 0)));
            series.getData().add(new XYChart.Data("8",s.totalPriceInMonth(8, 0)));
            series.getData().add(new XYChart.Data("9",s.totalPriceInMonth(9, 0)));
            series.getData().add(new XYChart.Data("10",s.totalPriceInMonth(10, 0)));
            series.getData().add(new XYChart.Data("11",s.totalPriceInMonth(11, 0)));
            series.getData().add(new XYChart.Data("12",s.totalPriceInMonth(12, 0)));

            barChart.getData().addAll(series);
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
     
        
    }

    public void renevueTotal (ActionEvent evt){

         try {
            ChartController c = new ChartController();
            if (c.isNumeric(txtYear.getText()) == false )
            {
                Utils.getBox("Giá trị nhập không hợp lệ!!!","CAN'T REVENUE CHART ", Alert.AlertType.ERROR).show();
                txtYear.setText("");
            }
            else if (Integer.parseInt(txtYear.getText())<= 2000 || Integer.parseInt(txtYear.getText())> 2021 ){
                 Utils.getBox("Năm không hợp lệ!!!","CAN'T REVENUE CHART", Alert.AlertType.ERROR).show();
                 txtYear.setText("");
            }
            else
            {
                StatisticalService s = new StatisticalService();
                XYChart.Series series = new XYChart.Series();
                series.getData().removeAll(Collections.singleton(barChart.getData().setAll()));

                series.setName(txtYear.getText());
                series.getData().add(new XYChart.Data("1",s.totalPriceInMonth(1, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("2",s.totalPriceInMonth(2, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("3",s.totalPriceInMonth(3, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("4",s.totalPriceInMonth(4, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("5",s.totalPriceInMonth(5, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("6",s.totalPriceInMonth(6, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("7",s.totalPriceInMonth(7, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("8",s.totalPriceInMonth(8, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("9",s.totalPriceInMonth(9, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("10",s.totalPriceInMonth(10, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("11",s.totalPriceInMonth(11, Integer.parseInt(txtYear.getText()))));
                series.getData().add(new XYChart.Data("12",s.totalPriceInMonth(12, Integer.parseInt(txtYear.getText()))));

              
                barChart.getData().addAll(series);
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isNumeric(String str) { 
      try {  
        Integer.parseInt(str);  
        return true;
      } catch(NumberFormatException e){  
        return false;  
      }  
    }

}
    

