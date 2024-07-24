module com.apd.traveldiary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.apd.traveldiary to javafx.fxml;
    exports com.apd.traveldiary;
}