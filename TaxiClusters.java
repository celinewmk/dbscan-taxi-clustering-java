import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.*;
/**
 * Classe TaxiClusters, l'application qui permet de trouver les clusters
 * @author Céline Wan Min Kee
 * Numéro étudiant: 300193369
 * Projet intégrateur: Partie 1 Java
 */

public class TaxiClusters {

    // variables d'instances
    private String nomFichier;
    private ArrayList<TripRecord> data;
    private ArrayList<Cluster> clusters;
    private int minPts; 
    private double eps;

    /**
     * Constructeur
     * @param nomFichier le nom du fichier csv
     */
    public TaxiClusters(String nomFichier) {
        this.nomFichier = nomFichier;
    }
    
    /**
     * Cette méthode traverse le fichier csv afin de créer une liste de TripRecord
     * @throws Exception
     */
    public void readFile() throws Exception {
        data = new ArrayList<TripRecord>(); // initialise la liste qui gardera les informations nécessaires de chaque TripRecord de la base de données
        int count = 0;                      // garde une trace de la ligne où nous nous trouvons dans la lecture du fichier csv
        Scanner scanner = new Scanner(new File(nomFichier));
        while (scanner.hasNext()) {

            //divise la ligne actuelle à la virgule pour extraire les données utiles de la ligne   
            String[] l = scanner.nextLine().split(","); 

            if(count != 0){
                // exécutée si nous ne sommes pas à la première ligne du fichier csv (où les attributs sont écrits)
                // ajout du trip record à la ArrayList<TripRecord> data
                TripRecord trip = new TripRecord(l[4], new GPScoord(Double.parseDouble(l[9]), Double.parseDouble(l[8])), 
                    new GPScoord(Double.parseDouble(l[13]), Double.parseDouble(l[12])), Float.parseFloat(l[7]));
                data.add(trip);
            }
            count++; // changement de ligne
        }
    } 

    /**
     * Cette méthode utilise l'algorithme de DBSCAN pour créer les clusters
     * L'algorithme de DBSCAN utilisé a été implémenté en utilisant le pseudocode donné dans le pdf
     * @param data   les données du fichier csv
     * @param eps    la valeur epsilon pour calculer la distance permettant d'identifier les points voisins d'un point donné
     * @param minPts le nombre minimum de voisins qu'un point peut avoir
     * @throws Exception
     */
    public void generateClusters(ArrayList<TripRecord> data, double eps, int minPts) throws Exception {
        this.eps = eps;         // initialise la valeur epsilon donnée
        this.minPts = minPts;   // initialise la valeur minPts (n) donnée
        int clusterCounter = 0; // initialise le cluster counter
        
        ArrayList<TripRecord> neighbours = new ArrayList<TripRecord>(); // initialise la liste pour garder les voisins
        clusters = new ArrayList<Cluster>();                            // initialise la liste pour garder les clusters

        for(TripRecord trip: data) {
            if(trip.getLabel() != "undefined"){             // point déjà traité
                continue;
            }
            
            neighbours = findNeighbours(data, trip, eps);   // trouve les voisins
            if (neighbours.size() < minPts) {               // vérifie la densité
                trip.setLabel("Noise");                     // défini le label du point à Noise
                continue;
            }
            
            clusterCounter++;  // le label du prochain cluster
            trip.setLabel(Integer.toString(clusterCounter));            // défini le label du point initial
            Cluster seedSet = new Cluster(neighbours, clusterCounter);  // création du cluster
            
            for(int i = 0; i < seedSet.getPoints().size(); i++) {       // traverse le cluster pour l'étendre
                if (seedSet.getPoints().get(i).getLabel() == "Noise") {
                    seedSet.getPoints().get(i).setLabel(Integer.toString(clusterCounter)); // Changement de Noise à un point au bord
                }
                if (seedSet.getPoints().get(i).getLabel() != "undefined") {              // point déjà traité
                    continue;
                }
                seedSet.getPoints().get(i).setLabel(Integer.toString(clusterCounter));   // label voisin
                neighbours = findNeighbours(data, seedSet.getPoints().get(i), eps);      // trouve les voisins
                if (neighbours.size() >= minPts) {                                       // vérifie la densité si c'est un point central
                    seedSet.addNeighbours(neighbours);                                   // ajoute le nouveau voisin au cluster
                }
            }
            clusters.add(seedSet); // ajoute le cluster à la liste de cluster (ArrayList<Cluster>)
        }
        writeAllClutersToFile();   // appelle la méthode pour écrire les clusters dans un fichier csv
    }

    /**
     * Cette méthode crée les fichiers csv pour les 10 plus grands clusters
     * @param data la liste de tous les clusters de la base de donnée
     * @throws Exception
     */
    public void findTenBiggest(ArrayList<Cluster> data) throws Exception {
        for (int i = 0; i < 10; i++) { //traverse 10 fois pour avoir les 10 plus grand clusters
            OutputStreamWriter outputFile = new OutputStreamWriter(new FileOutputStream("cluster_"+(data.get(i).getClusterId()-1)+".csv"));
            outputFile.write("label, Latitude, Longitude\n");
            for(TripRecord trip : data.get(i).getPoints()){
                outputFile.write(data.get(i).getClusterId() + ", "+ trip.getpickUpLocation().getLatitude() + ", " + trip.getpickUpLocation().getLongitude()+"\n");
            }

            outputFile.flush();
            outputFile.close();
        }
        // message montré à l'utilisateur
        System.out.println("Les fichiers csv des 10 plus grands clusters ont aussi été générés!\n");
    }

    /**
     * Cette méthode privée permet de trouver les voisins du point Q
     * @param listPoints les données du fichier csv
     * @param Q          le point de base
     * @param eps        la valeur epsilon pour calculer la distance permettant d'identifier les points voisins d'un point donné
     * @return une ArrayList<TripRecord> contenant tous les voisins du point Q
     */
    private ArrayList<TripRecord> findNeighbours(ArrayList<TripRecord> listPoints, TripRecord Q, double eps) {
        ArrayList<TripRecord> neighbours = new ArrayList<TripRecord>();    // initialise la liste de voisins
        for(TripRecord P: listPoints) {                                    // traverse toute la base de donnée
            if (Q.getpickUpLocation().calculateDist(P.getpickUpLocation()) <= eps) {  // calcule la distance et vérifie epsilon en appelant la fonction dans la classe GPScoord
                neighbours.add(P);                                         // ajout à la liste de voisins
            }
        }
        return neighbours;
    }

    /**
     * Cette méthode privée écrit les différents clusters du plus grand au plus petit dans un fichier csv
     * @throws Exception
     */
    private void writeAllClutersToFile() throws Exception {
        
        Collections.sort(clusters); // range les clusters du plus grand au plus petit en utilisant compareTo dans Cluster.java

        // création du fichier csv possédant tous les clusters générés
        OutputStreamWriter outputFile = new OutputStreamWriter(new FileOutputStream("output_with_eps" 
            + Double.toString(eps) +"_minPts " + Integer.toString(minPts) + "_for"+ nomFichier));
        
        // écriture des outputs dans le fichier
        outputFile.write("Cluster ID, Latitude, Longitude, Nombre de points\n");
        for(int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            outputFile.write(cluster.getClusterId()+", "+cluster.getPosition().getLatitude()+", "+
                cluster.getPosition().getLongitude()+", "+cluster.getNumPoints() + "\n");
            
        }
        outputFile.flush(); 
        outputFile.close();
        // message montré à l'utilisateur
        System.out.println("\nClustering terminé avec eps = " + this.eps + " et n = " + this.minPts + "!\nUn fichier csv a été généré avec tous les clusters de la base de données.\n");
    }

    // Fonction main de la classe
    public static void main(String args[]) throws Exception {

        TaxiClusters t = new TaxiClusters("yellow_tripdata_2009-01-15_1hour_clean.csv");

        t.readFile();                          // lecture du fichier
        t.generateClusters(t.data, 0.0003, 5); // génère les clusters avec eps = 0.0003 et n = 5 (ses valeurs peuvent changer)
        //t.findTenBiggest(t.clusters);        // décommenter la ligne pour également générer les 10 plus grands clusters
    }

}
