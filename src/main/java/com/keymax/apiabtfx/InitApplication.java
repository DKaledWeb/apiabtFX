package com.keymax.apiabtfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;

public class InitApplication extends Application {

    private final DeviceDetectionSIEM deviceDetectionSIEM;

    public InitApplication() {
        this.deviceDetectionSIEM = new DeviceDetectionSIEM();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InitApplication.class.getResource("siem-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Block Devices");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() {
        Thread thread = new Thread(this.deviceDetectionSIEM);
        thread.start();
    }

    @Override
    public void stop() {
        this.deviceDetectionSIEM.setThreadAlive(false);
    }
}