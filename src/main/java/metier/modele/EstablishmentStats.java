package metier.modele;

import java.util.List;


public class EstablishmentStats {
    /* Class that describes the stats per establishment, NOT BE PERSISTED INTO THE DB */
    private Establishment establishment;
    private long totalDemands;
    private double averageDurationMinutes;
    private List<String> topSubjects; 

    public EstablishmentStats(Establishment establishment, long totalDemands,
            double averageDurationMinutes, List<String> topSubjects) {
        this.establishment = establishment;
        this.totalDemands = totalDemands;
        this.averageDurationMinutes = averageDurationMinutes;
        this.topSubjects = topSubjects;
    }

    public Establishment getEstablishment() { return establishment; }
    public long getTotalDemands() { return totalDemands; }
    public double getAverageDurationMinutes() { return averageDurationMinutes; }
    public List<String> getTopSubjects() { return topSubjects; }
}
