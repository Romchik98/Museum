module com.museum.museum {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.datatransfer;

    opens com.museum.museum to javafx.fxml;
    opens com.museum.museum.controllers to javafx.fxml;
    exports com.museum.museum;
    exports com.museum.museum.controllers;
}