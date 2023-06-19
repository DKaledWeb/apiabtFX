package com.keymax.apiabtfx.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SIEMController extends Thread implements Initializable {

    @FXML
    public Label result;

    @FXML
    public void StartBtnOnAction(ActionEvent actionEvent) {
        result.setText(":D");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> System.out.println("Starting app..."));
    }
}
