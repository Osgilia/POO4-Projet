package modele;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Demands that are associated to a planning
 *
 * @author Henri, Lucas, Louis
 */
@Entity
public class PlannedDemand implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JoinColumn(name = "PLANNED_DEMAND_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    })
    private Demand demand;

    @JoinColumn(name = "PLANNNING_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    })
    private Planning planning;
    
    @JoinColumn(name = "NVEHICLEITINERARY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private VehicleItinerary vehicleItinerary;

    @JoinColumn(name = "NTECHNICIANITINERARY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TechnicianItinerary technicianItinerary;

    /**
     * No-argument constructor
     */
    public PlannedDemand() {
        this.demand = null;
        this.planning = null;
    }

    /**
     * Parameterized constructor
     *
     * @param planning
     * @param demand
     */
    public PlannedDemand(Planning planning, Demand demand) {
        this.demand = demand;
        this.planning = planning;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.demand);
        hash = 23 * hash + Objects.hashCode(this.planning);
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
        final PlannedDemand other = (PlannedDemand) obj;
        if (!Objects.equals(this.demand, other.demand)) {
            return false;
        }
        if (!Objects.equals(this.planning, other.planning)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.demand.toString();
    }
    
    public Customer getCustomer() {
        return this.demand.getCustomer();
    }

    public Demand getDemand() {
        return demand;
    }

    public Planning getPlanning() {
        return planning;
    }
    
    public int getFirstDay() {
        return this.demand.getFirstDay();
    }

    public int getLastDay() {
        return this.demand.getLastDay();
    }
    
    public VehicleItinerary getVehicleItinerary() {
        return vehicleItinerary;
    }

    public void setVehicleItinerary(VehicleItinerary vehicleItinerary) {
        this.vehicleItinerary = vehicleItinerary;
    }

    public TechnicianItinerary getTechnicianItinerary() {
        return technicianItinerary;
    }

    public void setTechnicianItinerary(TechnicianItinerary technicianItinerary) {
        this.technicianItinerary = technicianItinerary;
    }
    
    public int getTotalSizeMachines() {
        return this.demand.getTotalSizeMachines();
    }
    
    public MachineType getMachine() {
        return this.demand.getMachine();
    }
}