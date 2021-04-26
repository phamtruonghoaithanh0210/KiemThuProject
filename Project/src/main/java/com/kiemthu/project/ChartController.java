/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kiemthu.project;

import com.kiemthu.pojo.service.StatisticalService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;



/**
 *
 * @author Admin
 */
public class ChartController implements Initializable{
     
    @FXML
    private AreaChart<?, ?> AreaChart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        try {
            StatisticalService s = new StatisticalService();
            XYChart.Series series = new XYChart.Series();
            
            series.getData().add(new XYChart.Data("1",s.totalPriceInMonth(1, 2021)));
            series.getData().add(new XYChart.Data("2",s.totalPriceInMonth(2, 2021)));
            series.getData().add(new XYChart.Data("3",s.totalPriceInMonth(3, 2021)));
            series.getData().add(new XYChart.Data("4",s.totalPriceInMonth(4, 2021)));
            series.getData().add(new XYChart.Data("5",s.totalPriceInMonth(5, 2021)));
            series.getData().add(new XYChart.Data("6",s.totalPriceInMonth(6, 2021)));
            series.getData().add(new XYChart.Data("7",s.totalPriceInMonth(7, 2021)));
            series.getData().add(new XYChart.Data("8",s.totalPriceInMonth(8, 2021)));
            series.getData().add(new XYChart.Data("9",s.totalPriceInMonth(9, 2021)));
            series.getData().add(new XYChart.Data("10",s.totalPriceInMonth(10, 2021)));
            series.getData().add(new XYChart.Data("11",s.totalPriceInMonth(11, 2021)));
            series.getData().add(new XYChart.Data("12",s.totalPriceInMonth(12, 2021)));
            
            
           
            
            AreaChart.getData().addAll(series);
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        
    }



   

}
    

