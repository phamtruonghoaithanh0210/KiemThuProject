module com.kiemthu.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kiemthu.project to javafx.fxml;
    exports com.kiemthu.project;
}
