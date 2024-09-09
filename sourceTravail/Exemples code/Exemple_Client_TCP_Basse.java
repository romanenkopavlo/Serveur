package client_tcp_basse;

/**
 * @author Michael
 */

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client_TCP_Basse {
    public static void main(String[] args) throws InterruptedException {
        In clavier = new In();
        int PORT = 4000;
        Socket socket;
        PrintStream out;
        BufferedReader in;
        try {
            InetAddress adrLH = InetAddress.getLocalHost();
            socket = new Socket(adrLH, PORT);
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                //lecture message du serveur
                System.out.println("    MESSAGE SERVEUR >  \n      " + in.readLine() + "\n");
                //ecriture vers le serveur
                String requette = clavier.readString();
                out.println(requette);  // envoi reseau
                System.out.println("la requette " + requette);
                System.out.println("    MESSAGE SERVEUR >  \n      " + in.readLine() + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Client_TCP_Basse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



