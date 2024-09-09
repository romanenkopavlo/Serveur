package com.company;

import java.io.IOException;
import java.net.*;

public class Main {
    final static int PORT = 4219;
    final static int TAILLE = 1024;
    static byte data[] = new byte[TAILLE];

    public static void main(String[] args) throws UnknownHostException {
        try {
            // adr contient l'@IP de la partie serveur
            InetAddress adr = InetAddress.getByName("192.168.0.18");
            System.out.println(adr);
            // données à envoyer : chaîne de caractères
            byte[] data = (new String("time \n")).getBytes();
            // création du paquet avec les données et en précisant l'adresse du serveur
            // (@IP et port sur lequel il écoute : 7777)
            DatagramPacket packet = new DatagramPacket(data, data.length, adr, PORT);
            // création d'une socket, sans la lier à un port particulier
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(10000);
            // on envoie le paquet au serveur
            socket.send(packet);
            // attente paquet envoyé sur la socket du client
            byte[] dataReceive= new byte[512];
            DatagramPacket packetReceive = new DatagramPacket(dataReceive, dataReceive.length, adr, PORT);
            socket.receive(packetReceive);
            // récupération et affichage de la donnée contenue dans le paquet
            String chaine = new String(packetReceive.getData(), 0,
                    packetReceive.getLength());
            System.out.println(" recu du serveur : "+chaine);
        } catch (SocketTimeoutException ste) {
            System.out.println("Le delai pour la reponse a expire");
        } catch (IOException ex) {
        }

    }