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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    , @NamedQuery(name = "Vehicle.findById", query = "SELECT v FROM Vehicle v WHERE v.id = :id")
    , @NamedQuery(name = "Vehicle.findByInstance", query = "SELECT v FROM Vehicle v WHERE v.vInstance.id = :instanceId")})
public class Vehicle implements Serializable {

    /**
     * **********************
     * ATTRIBUTES *
     **********************
     */
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

    @OneToMany(mappedBy = "vehicle")
    private List<VehicleItinerary> itineraries;

    /**
     * Instance using this vehicle
     */
    @JoinColumn(name = "VINSTANCE", referencedColumnName = "ID")
    @OneToOne
    private Instance vInstance;

    /**
     * **************************
     * CONSTRUCTORS *
    ***************************
     */
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
     *
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
     *
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

    /**
     * ******************************
     * GETTERS & SETTERS *
     ******************************
     */
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

    public void setvInstance(Instance vInstance) {
        if (vInstance != null) {
            this.vInstance = vInstance;
            vInstance.setVehicle(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Vehicle other = (Vehicle) obj;
        if (Double.doubleToLongBits(this.capacity) != Double.doubleToLongBits(other.capacity)) {
            return false;
        }
        if (Double.doubleToLongBits(this.distanceMax) != Double.doubleToLongBits(other.distanceMax)) {
            return false;
        }
        if (Double.doubleToLongBits(this.distanceCost) != Double.doubleToLongBits(other.distanceCost)) {
            return false;
        }
        if (Double.doubleToLongBits(this.dayCost) != Double.doubleToLongBits(other.dayCost)) {
            return false;
        }
        if (Double.doubleToLongBits(this.usageCost) != Double.doubleToLongBits(other.usageCost)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.depot, other.depot)) {
            return false;
        }
        if (!Objects.equals(this.vInstance, other.vInstance)) {
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
