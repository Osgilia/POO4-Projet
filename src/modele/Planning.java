package modele;

import dao.DemandDao;
import dao.PlannedDemandDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyJoinColumn;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planning")
    private List<DayHorizon> days;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "planning")
    private List<PlannedDemand> plannedDemands;

    /**
     * No-argument constructor
     */
    public Planning() {
        this.cost = 0;
        this.nbDays = 0;
        this.days = new ArrayList<>();
        this.plannedDemands = new ArrayList<>();
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

    public double getCost() {
        return cost;
    }

    public List<PlannedDemand> getPlannedDemands() {
        return plannedDemands;
    }

    public List<DayHorizon> getDays() {
        return days;
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
    public boolean addDemand(Demand demand, PlannedDemandDao plannedDemandmanager, DemandDao demandManager) {
        if (demand != null) {
            Demand realDemand = demandManager.find(demand.getId());
            PlannedDemand plannedDemand = new PlannedDemand(this, realDemand);
            this.plannedDemands.add(plannedDemand);
            if (this.plannedDemands.contains(plannedDemand)) {
                boolean success = realDemand.addPlannedDemand(plannedDemand);
                demandManager.create(realDemand);
                return success;
            }
        }
        return false;
    }

    /**
     * Toggles the state of a demand : 0 to 1 if supplied, 1 to 2 if installed
     *
     * @param d : demand
     */
    public void toggleDemand(PlannedDemand d) {
        if (d != null) {
            if (d.getStateDemand() == 0) { // if is being supplied
                d.setStateDemand(1);
            } else if (d.getStateDemand() == 1) { // if is being installed
                d.setStateDemand(2);
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
            // @todo : method compute cost itineraries
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
        Set<MachineType> machinesUsed = new HashSet<>();
//        for (Map.Entry<PlannedDemand, Integer> demand : plannedDemands.entrySet()) {
            /**
             * @todo later : take into account penaltys associated to the
             * machines when they are not used for more than 1 day so far,
             * machines are always installed the day after the delivery method
             * "computeMachinesUsed"
             */
//        }
        this.cost = costPlanning;
    }

    /**
     * Computes overall distance travelled by all vehicles
     *
     * @return int
     */
    public int computeTruckDistance() {
        int truckDistance = 0;
        for (DayHorizon day : days) {
            for (Itinerary itinerary : day.getItineraries()) {
                if (itinerary instanceof VehicleItinerary) {
                    truckDistance += ((VehicleItinerary) itinerary).computeDistanceDemands(itinerary.getPoints());
                }
            }
        }
        return truckDistance;
    }

    /**
     * Computes overall distance travelled by all techniciqns
     *
     * @return int
     */
    public int computeTechnicianDistance() {
        int technicianDistance = 0;
        for (DayHorizon day : days) {
            for (Itinerary itinerary : day.getItineraries()) {
                if (itinerary instanceof TechnicianItinerary) {
                    technicianDistance += ((TechnicianItinerary) itinerary).computeDistanceDemands(itinerary.getPoints());
                }
            }
        }
        return technicianDistance;
    }

    /**
     * Computes the number of truck itineraries
     *
     * @return int
     */
    public int computeNbTruckDays() {
        int truckDays = 0;
        for (DayHorizon day : days) {
            for (Itinerary itinerary : day.getItineraries()) {
                if (itinerary instanceof VehicleItinerary) {
                    if (((VehicleItinerary) itinerary).getCost() != 0.0) {
                        truckDays++;
                    }
                }
            }
        }
        return truckDays;
    }

    /**
     * Computes the number of technician itineraries
     *
     * @return int
     */
    public int computeNbTechnicianDays() {
        int technicianDays = 0;
        for (DayHorizon day : days) {
            for (Itinerary itinerary : day.getItineraries()) {
                if (itinerary instanceof TechnicianItinerary) {
                    if (((TechnicianItinerary) itinerary).getCost() != 0.0) {
                        technicianDays++;
                    }
                }
            }
        }
        return technicianDays;
    }

    /**
     * Computes the maximum number of trucks used in a single day
     *
     * @return int
     */
    public int computeMaxTrucksUsed() {
        int maxTrucksUsed = 0, tmpMaxTrucksUsed = 0;
        for (DayHorizon day : days) {
            tmpMaxTrucksUsed = 0;
            for (Itinerary itinerary : day.getItineraries()) {
                if (itinerary instanceof VehicleItinerary) {
                    tmpMaxTrucksUsed++;
                }
            }
            if (tmpMaxTrucksUsed > maxTrucksUsed) {
                maxTrucksUsed = tmpMaxTrucksUsed;
            }
        }
        return maxTrucksUsed;
    }

    /**
     * Computes the total number of technicians used
     *
     * @return int
     */
    public int computeTotalNbTechniciansUsed() {
        Set<Technician> techniciansList = new HashSet<>();
        for (DayHorizon day : days) {
            for (Itinerary itinerary : day.getItineraries()) {
                if (itinerary instanceof TechnicianItinerary) {
                    techniciansList.add(((TechnicianItinerary) itinerary).getTechnician());
                }
            }
        }
        return techniciansList.size();
    }

    /**
     * Computes the cost of idle machines
     *
     * @todo : next solution
     * @return int
     */
    public int computeIdleMachineCosts() {
        return 0;
    }
}
