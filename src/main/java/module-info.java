module com.traveldiary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.traveldiary to javafx.fxml;
    opens com.traveldiary.controllers to javafx.fxml;
    exports com.traveldiary;
}
