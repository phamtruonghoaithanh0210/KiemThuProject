package com.kiemthu.project;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("secondary");
    }
}
