package com.example.projets3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstPageController implements Initializable {
    @FXML
    public Button ClientButton;
    public Button serverButton;
    public TextField pseudo;
    public TextArea message;
    public TextArea displayMessage;

    private String pseudonyme;

    @FXML
    protected void onClientButtonClick(ActionEvent event) throws IOException {

        pseudonyme = pseudo.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("validating-view.fxml"));
        Parent root = loader.load();

        ValidatingController validatingController = loader.getController();
        validatingController.setPseudonyme(pseudonyme);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Guessing game");
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    protected void onServerButtonClick(ActionEvent event) throws IOException {
        pseudonyme = pseudo.getText();
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ipAdress = inetAddress.getHostAddress();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Server-view.fxml"));
        Parent root = loader.load();

        ServerPageController serverPageController = loader.getController();
        serverPageController.setPseudo(pseudonyme);
        serverPageController.setIpaddress(ipAdress);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Guessing game");
        stage.show();
        stage.centerOnScreen();
    }

    public void onSendButtonClick(ActionEvent actionEvent) {
        displayMessage.setText("Impossible d'envoyer le message , veuiller d'abord vous mettre en réseau");
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        message.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
