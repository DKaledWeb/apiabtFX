module com.keymax.apiabtfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires okhttp3;
    requires org.jose4j;
    requires org.kordamp.bootstrapfx.core;


    opens com.keymax.apiabtfx to javafx.fxml;
    exports com.keymax.apiabtfx;
    exports com.keymax.apiabtfx.controller;
    opens com.keymax.apiabtfx.controller to javafx.fxml;
}