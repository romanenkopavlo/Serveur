package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
//        Outils outils = new Outils();
//        ArrayList<IpV4> mesInterfaces = outils.getSystemIp();
//        System.out.println(mesInterfaces.size());
//        System.out.println(mesInterfaces.get(0));
        final int port = 5000;
        char [] bufferEntree = new char[65535];
        String messageRecu;
        String reponse = null;
        ServerSocket monServeurDeSocket = new ServerSocket(port);
        System.out.println("Serveur en fonctionnement");

        while (true) {
            try {
                boolean deconnexionClientDemandee = false;
                Socket socketDuClient = monServeurDeSocket.accept();
                System.out.println("Connection avec: " + socketDuClient.getInetAddress());
                System.out.println("Le port: " + socketDuClient.getLocalPort());

                BufferedReader fluxEntree = new BufferedReader(new InputStreamReader(socketDuClient.getInputStream()));
                PrintStream fluxSortie = new PrintStream(socketDuClient.getOutputStream());

                while (!deconnexionClientDemandee && socketDuClient.isConnected()) {
                    System.out.println("attente...");
                    fluxSortie.println("Les commandes disponibles: HELLO, TIME, ECHO *votre phrase*, YOU, ME (exit pour finir)");
                    int NbLus = fluxEntree.read(bufferEntree);

                    if (NbLus >= 4) {
                        if ((bufferEntree[1] == 'X' || bufferEntree[1] == 'x')) {
                            messageRecu = new String(bufferEntree, 0, NbLus);
                        } else {
                            messageRecu = new String(bufferEntree, 0, NbLus - 2);
                        }
                    } else {
                        messageRecu = new String(bufferEntree, 0, NbLus);
                    }

                    switch (messageRecu.toUpperCase()) {
                        case "HELLO":
                            reponse = "Bienvenue";
                            break;
                        case "TIME":
                            LocalDateTime ld = LocalDateTime.now();
                            reponse = ld.toString();
                            break;
                        case "YOU":
                        case "WHOAREYOU?":
                            reponse = "IP: " + socketDuClient.getInetAddress().toString().substring(1) + "\tLe port: " + socketDuClient.getLocalPort();
                            break;
                        case "ME":
                        case "WHOAMI?":
                            reponse = "IP: " + InetAddress.getLocalHost().getHostAddress() + "\tLe port: " + socketDuClient.getPort();
                            break;
                        case "EXIT":
                            reponse = "JE VOUS DECONNECTE !!!";
                            deconnexionClientDemandee = true;
                            break;
                    }

                    if (messageRecu.toUpperCase().startsWith("ECHO")) {
                        reponse = messageRecu.substring(4);
                        reponse = reponse.trim();
                    }

                    fluxSortie.println(reponse);
                    System.out.println("\t\t Message emis: " + reponse);
                }
                fluxEntree.close();
                fluxSortie.close();
                socketDuClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}