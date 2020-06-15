package modele;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Demands that are associated to a planning
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "PLANNEDDEMAND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlannedDemand.findByStatedemandAndPlanning", query = "SELECT p FROM PlannedDemand p WHERE p.stateDemand = :stateDemand AND p.planning = :planning")
})
public class PlannedDemand implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "DEMAND_ID", referencedColumnName = "ID")
    @ManyToOne
    private Demand demand;

    @JoinColumn(name = "PLANNNING_ID", referencedColumnName = "ID")
    @ManyToOne
    private Planning planning;

    @JoinColumn(name = "VEHICLEITINERARY", referencedColumnName = "ID")
    @ManyToOne
    private VehicleItinerary vehicleItinerary;

    @JoinColumn(name = "NTECHNICIANITINERARY", referencedColumnName = "ID")
    @ManyToOne
    private TechnicianItinerary technicianItinerary;

    @Column(name = "STATEDEMAND")
    private int stateDemand;

    @Column(name = "POSITIONVEHICLE")
    private int positionVehicle;

    @Column(name = "POSITIONTECHNICIAN")
    private int positionTechnician;

    /**
     * No-argument constructor
     */
    public PlannedDemand() {
        this.demand = null;
        this.planning = null;
        this.stateDemand = 0;
        this.positionVehicle = -1;
        this.positionTechnician = -1;
    }

    /**
     * Parameterized constructor
     *
     * @param planning
     * @param demand
     */
    public PlannedDemand(Planning planning, Demand demand) {
        this();
        this.demand = demand;
        this.planning = planning;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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

    public int getPositionVehicle() {
        return positionVehicle;
    }

    public void setPositionVehicle(int positionVehicle) {
        this.positionVehicle = positionVehicle;
    }

    public int getPositionTechnician() {
        return positionTechnician;
    }

    public void setPositionTechnician(int positionTechnician) {
        this.positionTechnician = positionTechnician;
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

    public Integer getId() {
        return id;
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

    public int getStateDemand() {
        return stateDemand;
    }

    public void setStateDemand(int stateDemand) {
        this.stateDemand = stateDemand;
    }
}
