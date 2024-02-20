module com.example.vadasz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vadasz to javafx.fxml;
    exports com.example.vadasz;
}