/**
 * Classe TripRecord
 * @author Céline Wan Min Kee
 * Numéro étudiant: 300193369
 */

public class TripRecord {
    
    // variables d'instances
    private String label;
    private String pickup_DateTime;
    private GPScoord pickup_Location;
    private GPScoord dropoff_Location;
    private float trip_Distance;

    /**
     * Constructeur
     * @param pickup_DateTime  l'heure et la date de pick up
     * @param pickup_Location  les coordonnées pour récupérer
     * @param dropoff_Location les coordonnées pour déposer
     * @param trip_Distance    la distance du voyage
     */
    public TripRecord(String pickup_DateTime, GPScoord pickup_Location, GPScoord dropoff_Location, float trip_Distance){
        this.label = "undefined";
        this.pickup_DateTime = pickup_DateTime;
        this.pickup_Location = pickup_Location;
        this.dropoff_Location = dropoff_Location;
        this.trip_Distance = trip_Distance;
    }

    /**
     * Getter
     * @return le label du TripRecord
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter
     * Définit une nouvelle valeure pour le label du TripRecord
     * @param newLabel
     */
    public void setLabel(String newLabel) {
        label = newLabel;
    }
    
    /**
     * Getter
     * @return l'heure et la date pour récupérer
     */
    public String getPickUpDateTime() {
        return pickup_DateTime;
    }

    /**
     * Getter
     * @return les coordonnées pour récupérer
     */
    public GPScoord getpickUpLocation() {
        return pickup_Location;
    }

    /**
     * Getter
     * @return les coordonnées pour déposer
     */
    public GPScoord getDropOffLocation() {
        return dropoff_Location;
    }

    /**
     * Getter
     * @return la distance du voyage
     */
    public float getTripDistance() {
        return trip_Distance;
    }

    /**
     * Méthode toString qui imprime les données de la classe TripRecord
     */
    public String toString() {
        return (
            "Label: " + label + "\nPick Up Date/Time: " + pickup_DateTime + "\nPick Up Location: " +
            pickup_Location.toString() + "\nDropOff Location: " + dropoff_Location.toString() + "\nTrip Distance: " +
            trip_Distance 
        );
    }
}
