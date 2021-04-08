package com.kiemthu.project;

import com.kiemthu.pojo.Customer;
import com.kiemthu.pojo.Staff;
import com.kiemthu.pojo.User;
import com.kiemthu.pojo.service.CustomerService;
import com.kiemthu.pojo.service.StaffService;
import com.kiemthu.pojo.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws SQLException {
        launch();
        /* gi chu test ham
        UserService u = new UserService();
        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
        Staff staff = new Staff("thanh","chuheo25@gmail.com","voyen","181610","abc.jpg",true,date,date,"0945430117","thanh hoa",User.Role.Staff);
        u.addStaff(staff);
        //test khang hang
        UserService u = new UserService();
        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
        Customer customer = new Customer("yen","chuheo25@gmail.com","abc.jpg",true,date,date,"0945430117","thanh hoa",User.Role.Customer);
        System.out.println("..oke");
        u.addCustormer(customer);
        StaffService u = new StaffService();
        List<Staff> list1 = new ArrayList<>();
        list1 = u.getStaffs();
        for(int i =0;i<list1.size();i++){
                System.out.printf("\n%d\t%s\t\t%s\t%s\t\t%s",
                                   list1.get(i).getIduser(),list1.get(i).getName(),list1.get(i).getEmail(),list1.get(i).getUsername(),list1.get(i).getPassword());
                
        }
        //test ham lay danh khach hang
        CustomerService u = new CustomerService();
        List<Customer> list1 = new ArrayList<>();
        list1 = u.getCustomer();
        for(int i =0;i<list1.size();i++){
                System.out.printf("\n%d\t%s\t\t%s",
                                   list1.get(i).getIduser(),list1.get(i).getName(),list1.get(i).getEmail());
        }  */     
        StaffService u = new StaffService();
        System.err.println( u.searchByID(0)==null);
    }

}
