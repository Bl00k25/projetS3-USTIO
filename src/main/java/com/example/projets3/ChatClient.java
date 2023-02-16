package com.example.projets3;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;


public class ChatClient {

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    public ChatClient(Socket socket) {
        try {
        this.socket=socket;
        this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }catch (IOException ioe)
    {
        System.out.println("Erreur lors de la création du serveur");
        ioe.getStackTrace();
        closeEverything(socket,bufferedReader,bufferedWriter);
    }

}

    public  void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try{
            if(bufferedReader!=null)
            {
                bufferedReader.close();
            }
            if(bufferedWriter!=null)
            {
                bufferedWriter.close();
            }
            if(socket!=null)
            {
                socket.close();
            }

        }catch (IOException ioe)
        {
            ioe.getStackTrace();
        }
    }

    public void sendMessages(String message) {
        try{
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException ioException)
        {
            ioException.getStackTrace();
            System.out.println("Erreur lors de l'envoi du message");
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void receiveMessages(TextArea TA , String mess2disp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected())
                {
                    try {
                        String messageReceived = bufferedReader.readLine();
                        ClientPageController.displayIt(messageReceived, TA , mess2disp);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Erreur lors de la réception du message");
                        closeEverything(socket,bufferedReader,bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void getCode(int[] code)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (socket.isConnected())
                {
                    try {
                        int codeReceived = bufferedReader.read();
                        code[0]=codeReceived;
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Erreur lors de la réception du message");
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }

}
