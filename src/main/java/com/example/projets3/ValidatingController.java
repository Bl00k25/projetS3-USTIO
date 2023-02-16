package com.example.projets3;


import com.almasb.fxgl.net.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ValidatingController {
    public Button connect;
    public TextField getIP;

    private String pseudonyme;

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    @FXML
    protected void onConnectButtonClick(ActionEvent event) throws IOException {
        String ipAddress = getIP.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client-view.fxml"));
        Parent root = loader.load();

        ClientPageController clientPageController = loader.getController();
        clientPageController.setPseudo(pseudonyme);
        clientPageController.setIP(ipAddress);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Guessing game");
        stage.show();
        stage.centerOnScreen();
    }

}
