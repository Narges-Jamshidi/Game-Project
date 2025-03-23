module org.example.gameproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    requires mysql.connector.java;
    requires javafx.media;

    opens org.example.gameproject to javafx.fxml;
    exports org.example.gameproject;
    exports org.example.gameproject.view;
    opens org.example.gameproject.view to javafx.fxml;
    exports org.example.gameproject.view.maps;
    opens org.example.gameproject.view.maps to javafx.fxml;
    exports org.example.gameproject.view.playertasks;
    opens org.example.gameproject.view.playertasks to javafx.fxml;
}