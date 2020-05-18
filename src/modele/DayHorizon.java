/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private List<VehicleItinerary> vehicleItineraries;

    @OneToMany(mappedBy = "dayHorizon",
            cascade = {
                CascadeType.PERSIST
            })
    private List<TechnicianItinerary> technicianItineraries;

    @Column(name = "NUMBER")
    private int dayNumber;

    /**
     * No-argument constructor
     */
    public DayHorizon() {
        this.dayNumber = -1;
        this.vehicleItineraries = new ArrayList<>();
        this.technicianItineraries = new ArrayList<>();
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
        String str = "\tDay " + dayNumber + " :";
        for(VehicleItinerary v : vehicleItineraries) {
            str += "\n\t\t\t" + v;
        }
        str += "\n\t\t---";
//        for(TechnicianItinerary t : technicianItineraries) {
//            str += "\n\t\t\t" + t;
//        }
        return str;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    /**
     * Adds a vehicle itinerary to the current day
     *
     * @todo : refact with addTechnicianItinerary
     * @param itinerary
     * @return true if success
     */
    public boolean addVehiculeItinerary(VehicleItinerary itinerary) {
        if (itinerary != null) {
            this.vehicleItineraries.add(itinerary);
            if (this.vehicleItineraries.contains(itinerary)) {
                itinerary.setDayHorizon(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a technician itinerary to the current day
     *
     * @param itinerary
     * @return true if success
     */
    public boolean addTechnicianItinerary(TechnicianItinerary itinerary) {
        if (itinerary != null) {
            this.technicianItineraries.add(itinerary);
            if (this.technicianItineraries.contains(itinerary)) {
                itinerary.setDayHorizon(this);
                return true;
            }
        }
        return false;
    }
}
