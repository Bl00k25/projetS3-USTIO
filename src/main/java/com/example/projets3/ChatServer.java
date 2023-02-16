package com.example.projets3;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class ChatServer {
    private ServerSocket serverSocket;
    private  Socket socket;
    private  BufferedReader bufferedReader;
    private  BufferedWriter bufferedWriter;

    public ChatServer(ServerSocket serverSocket){
        try{
            this.serverSocket=serverSocket;
            socket = serverSocket.accept();
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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

    public  void sendMessages(String message2display) {
        try{
            bufferedWriter.write(message2display);
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

    public  void receiveMessages(TextArea TA , String mess2disp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected())
                {
                    try {
                        String messageReceived = bufferedReader.readLine();
                        ServerPageController.displayIt(messageReceived,TA , mess2disp);
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

    public  void sendCode(int toGuess) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    bufferedWriter.write(toGuess);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                    System.out.println("Erreur de l'envoi du code");
                    closeEverything(socket,bufferedReader,bufferedWriter);
                }

            }
        }).start();
    }
}
