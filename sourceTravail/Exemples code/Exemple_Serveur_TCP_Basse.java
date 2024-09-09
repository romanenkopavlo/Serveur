package serveur_tcp;

import java.io.*;
import java.net.*;

public class MonoServeur {
    
     
    public static void main(String[] args) throws IOException {
      
    final int port=4000;
    boolean deconnexionClientDemandee = false ;   
    char [ ] bufferEntree = new char [65535];
    String messageRecu = null;
    String reponse = null;
    //créer une instance de la classe SocketServer en précisant le port en paramètre
    ServerSocket monServerDeSocket = new ServerSocket(port);
    System.out.println("Serveur en fonctionnement.");

    //définir une boucle sans fin contenant les actions ci-dessous
    while (true) {
            //appelle de la méthode accept() qui renvoie une socket lors d'une nouvelle connexion
            Socket socketDuClient = monServerDeSocket.accept();
            System.out.println("Connexion avec : "+socketDuClient.getInetAddress());

            //obtenir un flux en entrée et en sortie à partir de la socket
            BufferedReader fluxEntree = new BufferedReader(new InputStreamReader(socketDuClient.getInputStream()));
            PrintStream fluxSortie = new PrintStream(socketDuClient.getOutputStream());

            //écrire les traitements à réaliser
            while (!deconnexionClientDemandee && socketDuClient.isConnected()){
                System.out.println("attente...");
                fluxSortie.println("Entrez une phase qui sera mise en majuscule par le serveur (exit pour finir)");
                //reception
                int NbLus = fluxEntree.read(bufferEntree);
                messageRecu= new String(bufferEntree, 0,NbLus);
                if (messageRecu.length() != 0 )
                {
                    System.out.println("\t\t Message reçu : " + messageRecu );
                }
                
                //TRAITEMENT
                if (messageRecu.equalsIgnoreCase("exit")){
                    reponse = "JE VOUS DECONNECTE !!!";
                    deconnexionClientDemandee = true;
                }else{                  
                    System.out.println("\t\t Traitement Requete ... ");
                    reponse=messageRecu.toUpperCase();
                    
                }
                fluxSortie.println(reponse);
                System.out.println("\t\t Message emis : "+reponse);
            }
            //client vient de se deconnecter !!!
            socketDuClient.close();
            //faut réarmé pour le client suivant
            deconnexionClientDemandee = false;
        }
    }
}    