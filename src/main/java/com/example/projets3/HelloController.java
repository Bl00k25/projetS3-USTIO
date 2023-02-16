package com.example.projets3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    public Label welcomeText;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws  IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("firstpage-view.fxml"));
        Parent root = loader.load();


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Guessing game");
        stage.show();
        stage.centerOnScreen();
    }
}