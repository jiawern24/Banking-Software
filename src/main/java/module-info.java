module com.bankinggui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bankinggui to javafx.fxml;
    exports com.bankinggui;
}