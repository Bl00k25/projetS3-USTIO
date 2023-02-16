package com.example.projets3;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientPageController implements Initializable {
    public Button sendMessage;
    public Button clearMessage;
    public TextArea message;
    public TextField pseudo;
    public TextArea displayMessage;
    public Spinner<Integer> numberGuessed;
    public Text Title;
    private ChatClient client;
    private String host;
    private int[] code = new int[1];
    private int essais = 3;


    private String message2display = "";

    public void setPseudo(String psd) {
        pseudo.setText(psd);
    }

    public static void displayIt(String messageReceived,TextArea dispMessage , String mess2disp) {
        mess2disp=dispMessage.getText();
        mess2disp+=messageReceived+"\n";
        if (!messageReceived.isBlank())
        {
            dispMessage.setText(mess2disp);
        }
    }

    public void setIP(String ipAddress) {
        host=ipAddress;
    }
    public void onSendButtonClick(ActionEvent actionEvent) {
        String toSend = pseudo.getText()+" : "+message.getText()+"\n";
        message2display=displayMessage.getText();
        message2display+=toSend;
        if(!(message==null)) {
            message.clear();
            displayMessage.setText(message2display);
            client.sendMessages(toSend);
        }
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        message.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerController spin = new SpinnerController(Title);
        spin.start();

        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200);
        spinnerValueFactory.setValue(0);
        numberGuessed.setValueFactory(spinnerValueFactory);

        try {
            client = new ChatClient(new Socket(host, 1234));
            message2display+="Connecté au serveur!!!\n";
            displayMessage.setText(message2display);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion au serveur");
        }

        client.getCode(code);
        client.receiveMessages(displayMessage,message2display);
    }

    public void onTryButtonClick(ActionEvent actionEvent) {
        essais--;
        int chosenOne = numberGuessed.getValue();
        if (chosenOne == code[0] && essais>=0)
        {
            message2display=displayMessage.getText();
            message2display+="Vous avez réussi à battre le maître du jeu\n";
            displayMessage.setText(message2display);
            client.sendMessages("Challenger a réussi à vous battre\n");
        }
        else if (chosenOne != code[0] && essais>=0){
            if(chosenOne>code[0])
            {
                message2display=displayMessage.getText();
                message2display+="Le nombre est plus petit que "+chosenOne+"\n";
                displayMessage.setText(message2display);
            }
            else
            {
                message2display=displayMessage.getText();
                message2display+="Le nombre est plus grand que "+chosenOne+"\n";
                displayMessage.setText(message2display);
            }
            message2display=displayMessage.getText();
            message2display+="Il vous reste "+essais+" essais\n";
            displayMessage.setText(message2display);
            client.sendMessages("Il reste "+essais+" essais au challenger\n");
            if(essais==0) {
                message2display=displayMessage.getText();
                message2display+="Vous avez échoué\n";
                displayMessage.setText(message2display);
                client.sendMessages("Challenger ne vous est pas arrivé.e à la cheville\n");
                essais=3;
            }
        }
    }
}
