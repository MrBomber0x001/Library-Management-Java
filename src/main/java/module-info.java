module com.example.demofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;


    opens com.example.demofx to javafx.fxml;
    exports com.example.demofx;
}