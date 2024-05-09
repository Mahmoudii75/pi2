module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires javafx.swing;

    requires org.apache.pdfbox;
    requires javafx.media;
    requires jdk.jsobject;
    requires javafx.web;
    exports com.example.demo1.service;
    exports com.example.demo1.entity;
    opens com.example.demo1.service to javafx.fxml;
    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
}