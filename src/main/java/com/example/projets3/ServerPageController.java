package com.example.projets3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerPageController implements Initializable {
    public Button sendNumber;
    public Text ipaddress;
    @FXML
    public Spinner<Integer> number2guess;
    public Button sendMessage;
    public Button clearMessage;
    public TextArea message;
    public TextField pseudonymie;
    public TextArea displayMessage ;
    public Text Title;

    private String message2display = "";

    private ChatServer server;

    public static void displayIt(String messageReceived,TextArea dispMessage , String mess2disp) {
        mess2disp=dispMessage.getText();
        mess2disp+=messageReceived+"\n";
        if (!messageReceived.isBlank())
        {
            dispMessage.setText(mess2disp);
        }
    }

    public  TextField getPseudonymie() {
        return pseudonymie;
    }

    public void setPseudo(String psd) {
         pseudonymie.setText(psd);
    }

    public void setIpaddress(String ipadr) {
        ipaddress.setText(ipadr);
    }

    public void onSendButtonClick(ActionEvent actionEvent) {
        String toSend = pseudonymie.getText()+" : "+message.getText()+"\n";
        message2display=displayMessage.getText();
        message2display+=toSend;
        if(!(message==null)) {
            message.clear();
            displayMessage.setText(message2display);
            server.sendMessages(toSend);
        }
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        message.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerController spin = new SpinnerController(Title);
        spin.start();

        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200);
        spinnerValueFactory.setValue(0);
        number2guess.setValueFactory(spinnerValueFactory);

        sendNumber.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                message2display+="Mise en marche du serveur...\n";
                displayMessage.setText(message2display);

                try{
                    server = new ChatServer(new ServerSocket(1234));
                    message2display += "Serveur en marche...\n";
                    displayMessage.setText(message2display);
                } catch (IOException e)
                {
                    e.printStackTrace();
                    System.out.println("Erreur lors de la création du serveur");
                }

                int toGuess = number2guess.getValue();
                server.sendCode(toGuess);

                server.receiveMessages(displayMessage,message2display);
            }
        });



    }
}
