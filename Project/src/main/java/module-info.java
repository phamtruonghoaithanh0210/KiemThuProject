module com.kiemthu.project {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires java.sql;
    opens com.kiemthu.project to javafx.fxml;
    exports com.kiemthu.project;
    exports com.kiemthu.pojo;
    requires org.apache.commons.codec;
}
