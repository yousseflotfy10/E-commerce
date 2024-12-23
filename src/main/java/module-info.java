module com.example.test10000 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.test10000 to javafx.fxml;
    exports com.example.test10000;
}