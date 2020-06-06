package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity that represents a day from a planning horizon
 *
 * @author Lucas, Louis, Henri
 */
@Entity
public class DayHorizon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "PLANNING", referencedColumnName = "ID")
    @ManyToOne
    private Planning planning;

    @OneToMany(mappedBy = "dayHorizon",
            cascade = {
                CascadeType.PERSIST
            })
    private List<Itinerary> itineraries;

    @Column(name = "NUMBER")
    private int dayNumber;

    @Column(name = "COST")
    private double cost;

    /**
     * No-argument constructor
     */
    public DayHorizon() {
        this.dayNumber = -1;
        this.cost = 0.0;
        this.itineraries = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param dayNumber
     */
    public DayHorizon(int dayNumber) {
        this();
        this.dayNumber = dayNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + this.dayNumber;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DayHorizon other = (DayHorizon) obj;
        if (this.dayNumber != other.dayNumber) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "\tDay " + dayNumber + " with a cost of " + cost + " :";
        for (Itinerary i : itineraries) {
            str += "\n\t\t\t" + i;
        }
        return str;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public double getCost() {
        return cost;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    /**
     * Updates the cost of the day during the horizon
     */
    public void updateCost() {
        double costDay = 0.0;
        for (Itinerary itinerary : this.itineraries) {
            if (itinerary instanceof VehicleItinerary) {
                costDay += ((VehicleItinerary) itinerary).computeCostItinerary();
            }
            if (itinerary instanceof TechnicianItinerary) {
                costDay += ((TechnicianItinerary) itinerary).computeCostItinerary();
            }
        }
        this.cost = costDay;
        this.planning.updateCost();
    }

    /**
     * Adds an itinerary to the current day
     *
     * @param itinerary
     * @return true if success
     */
    public boolean addItinerary(Itinerary itinerary) {
        if (itinerary != null) {
            this.itineraries.add(itinerary);
            if (this.itineraries.contains(itinerary)) {
                itinerary.setDayHorizon(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Toggles the state of a demand in the planning
     *
     * @param d : planned demand to toggle
     */
    public void toggleDemand(PlannedDemand d) {
        this.planning.toggleDemand(d);
    }
}
