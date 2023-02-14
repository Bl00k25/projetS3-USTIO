package com.example.projets3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("entrer l'adresse ip du server:");
            String localhost=sc.nextLine();
            Socket client = new Socket(localhost, 6666);
            System.out.println("Le client s'est connecté");

            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            System.out.println("Entrez votre nom: ");
            String name = scanner.nextLine();
            out.writeUTF(name);

            Receiver receiver = new Receiver(in);
            receiver.start();

            String message = "";
            while (!message.equals("exit")) {
                message = scanner.nextLine();
                out.writeUTF(message);
                out.flush();
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Receiver extends Thread {
    DataInputStream in;

    public Receiver(DataInputStream in) {
        this.in = in;
    }

    public void run() {
        while (true) {
            try {
                String message = in.readUTF();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
