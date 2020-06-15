package modele;

import dao.DemandDao;
import dao.PlannedDemandDao;
import dao.VehicleItineraryDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    , @NamedQuery(name = "Planning.findByCost", query = "SELECT p FROM Planning p WHERE p.cost = :cost")
    , @NamedQuery(name = "Planning.findByAlgoNameAndInstance", query = "SELECT p FROM Planning p WHERE p.algoName = :algoName AND p.ninstance = :ninstance")})
public class Planning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ALGONAME")
    private String algoName;

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

    @OneToMany(mappedBy = "planning")
    private List<PlannedDemand> plannedDemands;

    /**
     * No-argument constructor
     */
    public Planning() {
        this.cost = 0;
        this.nbDays = 0;
        this.days = new ArrayList<>();
        this.plannedDemands = new ArrayList<>();
        this.algoName = "MinimalSolution";
    }

    /**
     * Parameterized constructor
     *
     * @param nbDays
     */
    public Planning(int nbDays, String algoName) {
        this();
        this.nbDays = nbDays;
        this.algoName = algoName;

    }

    /**
     * Parameterized constructor
     *
     * @param ninstance
     * @param nbDays
     */
    public Planning(Instance ninstance, int nbDays, String algoName) {
        this();
        this.algoName = algoName;
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

    public Integer getId() {
        return id;
    }

    public Instance getNinstance() {
        return ninstance;
    }

    public List<PlannedDemand> getPlannedDemands() {
        return plannedDemands;
    }

    public List<DayHorizon> getDays() {
        return days;
    }

    private Vehicle getVehicleInstance() {
        return this.ninstance.getVehicle();
    }

    public String getAlgoName() {
        return algoName;
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
    public boolean addDemand(Demand demand, PlannedDemandDao plannedDemandmanager) {
        if (demand != null) {
            PlannedDemand plannedDemand = new PlannedDemand(this, demand);
            this.plannedDemands.add(plannedDemand);
            if (this.plannedDemands.contains(plannedDemand)) {
                plannedDemandmanager.create(plannedDemand);
                return true;
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

        Set<Technician> techniciansUsed = new HashSet<>();

        for (DayHorizon day : days) {
            //we get the day costs
            costPlanning += day.getCost();

            //we want to know the used cost
            for (Itinerary itinerary : day.getItineraries()) {
                //if it's a technician Itinerary
                if (itinerary instanceof TechnicianItinerary) {
                    //if the itinerary is used and the technician is not already in the list
                    if (!(((TechnicianItinerary) itinerary).getCustomersDemands().isEmpty())) {
                        //we add the technician to the list
                        techniciansUsed.add(((TechnicianItinerary) itinerary).getTechnician());
                    }
                }
            }
        }

        System.err.println(techniciansUsed.size());
        for (Technician t : techniciansUsed) {
            costPlanning += t.getUsageCost();
        }
        //on recupere le cout journalier des camions
        costPlanning += this.getVehicleInstance().getUsageCost() * this.computeMaxTrucksUsed();

        //costPlanning += (double) this.computeIdleMachineCosts();
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
                    if (((VehicleItinerary) itinerary).getCustomersDemands().size() > 0) {
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
                    if (((TechnicianItinerary) itinerary).getCustomersDemands().size() > 0) {
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
                    if (((VehicleItinerary) itinerary).getCustomersDemands().size() > 0) {
                        tmpMaxTrucksUsed++;
                    }
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
                if (itinerary instanceof TechnicianItinerary && ((TechnicianItinerary) itinerary).getCustomersDemands().size() > 0) {
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
        int idleMachinesCost = 0;
        for (PlannedDemand demand : plannedDemands) {
            int deliveryDay = demand.getVehicleItinerary().getDayNumber();
            int installationDay = demand.getTechnicianItinerary().getDayNumber();
            int uselessDays = installationDay - deliveryDay;
            if (uselessDays > 1) {
                idleMachinesCost += (uselessDays - 1) * demand.getMachine().getPenalty() * demand.getDemand().getNbMachines();
            }
        }
        return idleMachinesCost;
    }
}
