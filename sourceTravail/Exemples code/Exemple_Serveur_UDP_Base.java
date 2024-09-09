/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_udp_base;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Michael
 */
public class Serveur_UDP_Base {
    final static int PORT = 5000;
    final static int TAILLE = 512;
    static byte buffer[] = new byte[TAILLE];
    
    public static void main(String[] args) throws SocketException, IOException {
        // création d'une socket liée au port 7777
        DatagramSocket socket = new DatagramSocket(PORT);
        
        System.out.println("Lancement du serveur");
        while (true) {
            // création d'un paquet en utilisant le tableau d'octets
            DatagramPacket packet = new DatagramPacket(buffer, TAILLE);
            //attente de la réception d'un paquet. Le paquet reçu est placé dans
            // packet et ses données dans data.
            socket.receive(packet);
            // récupération et affichage des données (une chaîne de caractères)
            String chaine = new String(packet.getData(), 0, packet.getLength());
            System.out.println(" Donnees reçues : "+chaine);
            System.out.println(" ca vient de :"+packet.getAddress()+":"+ packet.getPort());
            // on met une nouvelle donnée dans le paquet
            // (qui contient donc le couple @IP/port de la socket coté client)
            packet.setData((new String(chaine.toUpperCase())).getBytes());
            // on envoie le paquet au client
            socket.send(packet);
            
            
        }
    }
    
}
