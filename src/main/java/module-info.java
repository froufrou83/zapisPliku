module com.example.zapispliku {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zapispliku to javafx.fxml;
    exports com.example.zapispliku;
}