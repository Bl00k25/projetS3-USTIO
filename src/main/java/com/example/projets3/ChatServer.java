package com.example.projets3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    static ArrayList<Socket> clients = new ArrayList<>();
    static ArrayList<String> names = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(6666);
            System.out.println("Le serveur est en marche...");
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            System.out.println("Adresse IP de ce server : " + ipAddress);

            while (true) {
                /*socket clients contient des valeurs comme l'adreesse ip du clients
                * le numero de port utiliser par le client ,le statut de connection du client et aussi les donner envoyer par le client*/
                Socket client = server.accept();//cette ligne joue  le role de blocage si le clients est deja dans le server
                System.out.println("Le client s'est connecté");
                clients.add(client);
                //in chaine de caracter envoyer par le clients vers le server
                DataInputStream in = new DataInputStream(client.getInputStream());
                //out chaine de caractere envoyer par le server a ce clients
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                String name = in.readUTF();//nom du clients saisi par le client dans le code ChatClient
                names.add(name);

                ChatHandler handler = new ChatHandler(client, in, out, name, clients, names);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//thread execute la classe run
/*classe thread permet de faire des execution en parallele
tels que l'utilisation simultané de l'envoie et de la reception de message */
class ChatHandler extends Thread {
    Socket client;
    DataInputStream in;
    DataOutputStream out;
    String name;
    ArrayList<Socket> clients;
    ArrayList<String> names;

    //

    public ChatHandler(Socket client, DataInputStream in, DataOutputStream out, String name, ArrayList<Socket> clients, ArrayList<String> names) {
        this.client = client;
        this.in = in;
        this.out = out;
        this.name = name;
        this.clients = clients;
        this.names = names;
    }

    public void run() {
        String message = "";
        while (!message.equals("exit")) {
            try {
                message = in.readUTF();
                for (int i = 0; i < clients.size(); i++) {
                    Socket tempClient = clients.get(i);
                    DataOutputStream tempOut = new DataOutputStream(tempClient.getOutputStream());
                    tempOut.writeUTF(name + ": " + message);
                    tempOut.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
