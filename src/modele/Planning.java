package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Planning solution of an instance
 *
 * @author Lucas, Louis, Henri
 */
@Entity
@Table(name = "PLANNING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planning.findAll", query = "SELECT p FROM Planning p")
    , @NamedQuery(name = "Planning.findById", query = "SELECT p FROM Planning p WHERE p.id = :id")
    , @NamedQuery(name = "Planning.findByCost", query = "SELECT p FROM Planning p WHERE p.cost = :cost")})
public class Planning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "COST")
    private double cost;

    @JoinColumn(name = "NINSTANCE", referencedColumnName = "ID")
    @ManyToOne
    private Instance ninstance;

    /**
     * Number of days in the planning horizon
     */
    @Column(name = "NBDAYS")
    private int nbDays;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "nplanning")
    private List<DayHorizon> days;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "nplanning")
    private Map<Demand, Integer> demands;

    /**
     * No-argument constructor
     */
    public Planning() {
        this.cost = 0;
        this.nbDays = 0;
        this.days = new ArrayList<>();
        this.demands = new HashMap<>();
    }

    /**
     * Parameterized constructor
     *
     * @param nbDays
     */
    public Planning(int nbDays) {
        this();
        this.nbDays = nbDays;
    }

    /**
     * Parameterized constructor
     *
     * @param ninstance
     * @param nbDays
     */
    public Planning(Instance ninstance, int nbDays) {
        this();
        this.ninstance = ninstance;
        this.nbDays = nbDays;
    }

    public void setNinstance(Instance ninstance) {
        this.ninstance = ninstance;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Planning other = (Planning) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.ninstance, other.ninstance)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "Planning spread over " + nbDays + " days with a cost of " + cost;
        for (DayHorizon day : days) {
            str += "\n\t" + day;
        }
        return str;
    }

    public int getNbDays() {
        return nbDays;
    }

    public Map<Demand, Integer> getDemands() {
        return demands;
    }

    /**
     * Adds a day in the planning horizon
     *
     * @param dayHorizon
     * @return true if success
     */
    public boolean addDayHorizon(DayHorizon dayHorizon) {
        int dayNumber = dayHorizon.getDayNumber();
        if (dayNumber > 0 && dayNumber <= nbDays) {
            this.days.add(dayHorizon);
            if (this.days.contains(dayHorizon)) {
                dayHorizon.setPlanning(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a customer's request to the planning
     *
     * @param demand
     * @return true if success
     */
    public boolean addDemand(Demand demand) {
        if (demand != null) {
            this.demands.put(demand, 0);
            return true;
        }
        return false;
    }

    /**
     * Toggles the state of a demand : 0 to 1 if supplied, 1 to 2 if installed
     *
     * @param d : demand
     */
    public void toggleDemand(Demand d) {
        if (d != null) {
            if (this.demands.get(d) == 0) { // if is being supplied
                this.demands.put(d, 1);
            } else if (this.demands.get(d) == 1) { // if is being installed
                this.demands.put(d, 2);
            }
        }
    }

    /**
     * Updates the cost the of the planning using the computed cost of each day
     * and the cost of using each vehicle
     *
     * @todo : take into account penaltys associated to the machines
     * @todo : simplify code
     */
    public void updateCost() {
        double costPlanning = 0.0;
        Set<Vehicle> vehiclesUsed = new HashSet<>();
        Set<Technician> techniciansUsed = new HashSet<>();
        for (DayHorizon day : days) {
            costPlanning += day.getCost();
            // method compute cost itineraries
            for (Itinerary itinerary : day.getItineraries()) {
                if (itinerary instanceof VehicleItinerary) {
                    Vehicle vehicle = ((VehicleItinerary) itinerary).getVehicle();
                    if (!vehiclesUsed.contains(vehicle)) {
                        costPlanning += vehicle.getUsageCost();
                        vehiclesUsed.add(vehicle);
                    }
                }
                if (itinerary instanceof TechnicianItinerary) {
                    Technician technician = ((TechnicianItinerary) itinerary).getTechnician();
                    if (!techniciansUsed.contains(technician)) {
                        costPlanning += technician.getUsageCost();
                        techniciansUsed.add(technician);
                    }
                }
            }
        }
        Set<Machine> machinesUsed = new HashSet<>();
        for (Map.Entry<Demand, Integer> demand : demands.entrySet()) {
            /**
             * todo later : take into account penaltys associated to the machines
             * when they are not used for more than 1 day
             * so far, machines are always installed the day after the delivery
             * method "computeMachinesUsed"
             */
        }
        this.cost = costPlanning;
    }
}
