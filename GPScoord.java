/**
 * Classe GPScoord
 * @author Céline Wan Min Kee
 * Numéro étudiant: 300193369
 */

public class GPScoord {

    // variables d'instances
    private double latitude;
    private double longitude;
    
    /**
     * Constructeur
     * @param latitude  la coordonnée x (verticale)
     * @param longitude la coordonnée y (horizontale)
     */
    public GPScoord(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter
     * @return la valeur de la coordonnée latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter
     * @return la valeur de la coordonnée longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Méthode toString qui imprime les données de la classe GPScoord
     */
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }

    /**
     * Cette méthode permet de calculer la distance euclidienne entre 2 points
     * @param P un point 
     * @return la distance euclidienne entre les 2 points
     */
    public double calculateDist(GPScoord P){
        // utilise la formule sqrt((x - x')^2 + (y - y')^2) pour calculer la distance euclidienne
        return Math.sqrt(Math.pow(this.getLatitude()-P.getLatitude(),2) + Math.pow(this.getLongitude()-P.getLongitude(),2));
    }
}
