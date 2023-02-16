package com.example.projets3;


import com.almasb.fxgl.net.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ValidatingController implements Initializable {
    public Button connect;
    public TextField getIP;
    public Text Title;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerController spin = new SpinnerController(Title);
        spin.start();
    }
}
