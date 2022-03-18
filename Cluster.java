import java.util.ArrayList;
/**
 * Classe Cluster
 * @author Céline Wan Min Kee
 * Numéro étudiant: 300193369
 */

public class Cluster implements Comparable<Cluster> {

    // variables d'instance
    private int clusterId;
    private ArrayList<TripRecord> points;
    private GPScoord position;
    private int numPoints;

    /**
     * Constructeur
     * @param points les points appartenant au cluster
     * @param id     le id du cluster donné
     */
    public Cluster(ArrayList<TripRecord> points, int id) {
        clusterId = id;
        this.points = points;
        numPoints = points.size();
        setPosition();
    }

    /**
     * Getter
     * @return le id du cluster
     */
    public int getClusterId() {
        return clusterId;
    }

    /**
     * Getter
     * @return une ArrayList<TripRecord> qui contient les points du cluster
     */
    public ArrayList<TripRecord> getPoints() {
        return points;
    }

    /**
     * Getter
     * @return les coordonnées du cluster
     */
    public GPScoord getPosition() {
        return position;
    }

    /**
     * Setter
     * Définit les coordonnées du cluster
     */
    public void setPosition() {
        double latitude = 0;           // initialise la somme de la latitude à 0
        double longitude = 0;          // initialise la somme de la longitude à 0

        for(TripRecord trip: points) {
            latitude += trip.getpickUpLocation().getLatitude();   // ajout de toutes les valeurs latitude dans le cluster
            longitude += trip.getpickUpLocation().getLongitude(); // ajout de toutes les valeurs longitude dans le cluster
        }

        latitude = latitude / points.size();    //  trouve la moyenne pour la latitude
        longitude = longitude / points.size();  //  trouve la moyenne pour la longitude
        this.position = new GPScoord(latitude, longitude); // définit la position du cluster grâce aux moyennes calculées
    }

    /**
     * Getter
     * @return le nombre de points dans le cluster
     */
    public int getNumPoints() {
        return points.size();
    }

    /**
     * Cette méthode ajoute des voisins au cluster
     * @param neighbours une ArrayList<TripRecord> contenant les points à ajouter au cluster
     */
    public void addNeighbours(ArrayList<TripRecord> neighbours) {
        for(TripRecord trip: neighbours){        // traverse la liste de voisins à ajouter
            if(!this.points.contains(trip)) {    // vérifie si le point se trouve déjà dans le cluster
                this.points.add(trip);           // si non, ajout dans le cluster
                this.numPoints++;                // agrandissement du cluster
            }
        }
        setPosition();                    // définit le nouveau positionnement du cluster
    }

    /**
     * Méthode toString qui imprime les données de la classe Cluster
     */
    public String toString() {
        return (
            "Cluster ID: " + clusterId + ", Latitude: " + position.getLatitude() + 
            ", Longitude: " + position.getLongitude() + " Number of points: " + numPoints + "\n"
        );
    }

    @Override
    /**
     * Méthode compareTo (Comparable) qui nous permet de comparer deux Clusters par leur largeur (numPoints)
     * @param other Cluster à comparer avec l'instance
     * @return un entier 
     */
    public int compareTo(Cluster other) {
        if (this.getNumPoints() > other.getNumPoints()) {
            return -1;
        } 
        else if (this.getNumPoints() < other.getNumPoints()) {
            return 1;
        }
        return 0;
    }
  
}
