package com.kiemthu.project;

import java.io.IOException;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
        
    }
}