module com.github.dkw87.chronostt {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens com.github.dkw87.chronostt to javafx.fxml;
    exports com.github.dkw87.chronostt;
}