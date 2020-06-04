package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * Represents a vehicle
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "VEHICLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicle.findAll", query = "SELECT v FROM Vehicle v")
    , @NamedQuery(name = "Vehicle.findById", query = "SELECT v FROM Vehicle v WHERE v.id = :id")})
public class Vehicle implements Serializable {

    /************************
     *      ATTRIBUTES      *
     ***********************/
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "CAPACITY")
    private double capacity;

    @Column(name = "DISTANCEMAX")
    private double distanceMax;
    
    /**
     * Cost per distance unit covered by the vehicle
     */
    @Column(name = "DISTANCECOST")
    private double distanceCost;

    /**
     * Daily cost of the vehicle
     */
    @Column(name = "DAYCOST")
    private double dayCost;
    
    /**
     * Cost of using a technician during a day of the planning horizon
     */
    @Column(name = "USAGECOST")
    private double usageCost;

    @JoinColumn(name = "DEPOT", referencedColumnName = "ID")
    @ManyToOne
    private Depot depot;
    
    @OneToMany(mappedBy = "itinerary",
            cascade = {
                CascadeType.PERSIST
            })
    private List<VehicleItinerary> itineraries;
    
    /**
     * Instance using this vehicle
     */
    @JoinColumn(name = "VINSTANCE", referencedColumnName = "ID")
    @ManyToOne
    private Instance vInstance;
    
    
    /****************************
    *       CONSTRUCTORS        *
    ****************************/

    /**
     * No-argument constructor
     */
    public Vehicle() {
        this.capacity = 0.0;
        this.distanceMax = 0.0;
        this.distanceCost = 0.0;
        this.dayCost = 0.0;
        this.usageCost = 0.0;
        this.itineraries = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     * @param id
     * @param depot
     * @param capacity
     * @param distanceMax
     * @param distanceCost
     * @param dayCost
     * @param usageCost
     */
    public Vehicle(Integer id, Depot depot, double capacity, double distanceMax, double distanceCost, double dayCost, double usageCost) {
        this();
        this.id = id;
        this.capacity = capacity > 0 ? capacity : 0;
        this.distanceMax = distanceMax > 0 ? distanceMax : 0.0;
        this.distanceCost = distanceCost > 0 ? distanceCost : 0.0;
        this.dayCost = dayCost > 0 ? dayCost : 0.0;
        this.depot = depot;
        this.usageCost = usageCost;
    }

    /**
     * Copy constructor
     * @param v 
     */
    public Vehicle(Vehicle v) {
        this();
        this.capacity = v.getCapacity();
        this.depot = v.getDepot();
        this.dayCost = v.getDayCost();
        this.distanceCost = v.getDistanceCost();
        this.distanceMax = v.getDistanceMax();
    }
    
    
    /********************************
     *      GETTERS & SETTERS       *
     *******************************/

    public double getDistanceMax() {
        return distanceMax;
    }
    
    public double getDistanceCost() {
        return distanceCost;
    }

    public double getDayCost() {
        return dayCost;
    }

    public double getCapacity() {
        return capacity;
    }

    public Depot getDepot() {
        return depot;
    }

    public double getUsageCost() {
        return usageCost;
    }
    
    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Instance getvInstance() {
        return vInstance;
    }

    public void setvInstance(Instance vInstance) {
        this.vInstance = vInstance;
    }
    
    
    /************************
     *       METHODS        *
     ***********************/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vehicule (" + id + ")";
    }

    public void addItinerary(VehicleItinerary v) {
        this.itineraries.add(v);
    }
}
