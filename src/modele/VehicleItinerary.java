package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents an ordered sequence in which customers are supplied
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "VEHICLEITINERARY")
@XmlRootElement
@NamedQueries({})
@DiscriminatorValue("1")
public class VehicleItinerary extends Itinerary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;

    @Column(name = "COST")
    private double cost;

    @Column(name = "DISTANCETRAVELLED")
    private double distanceTravelled;

    @Column(name = "CAPACITYUSED")
    private double capacityUsed;

    @OneToMany(mappedBy = "vehicle_itinerary",
            cascade = {
                CascadeType.PERSIST
            })
    private List<PlannedDemand> customersDemands;

    /**
     * No-argument constructor
     */
    public VehicleItinerary() {
        super();
        this.cost = 0.0;
        this.capacityUsed = 0.0;
        this.distanceTravelled = 0.0;
        this.customersDemands = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param vehicle
     */
    public VehicleItinerary(Vehicle vehicle) {
        super(1);
        this.vehicle = vehicle;
        this.customersDemands = new ArrayList<>();
        this.addItineraryToVehicle();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof VehicleItinerary)) {
            return false;
        }
        VehicleItinerary other = (VehicleItinerary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "- Itinerary of " + vehicle + " [Capacity = " + capacityUsed + "/" + this.vehicle.getCapacity() + " | Distance = "
                + distanceTravelled + "/" + this.vehicle.getDistanceMax() + " | Cost = " + cost + "] :";
        for (PlannedDemand demand : customersDemands) {
            str += "\n\t\t\t\t " + demand;
        }
        return str;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getVehicleCapacity() {
        return vehicle.getCapacity();
    }

    public double getVehicleCapacityUsed() {
        return this.capacityUsed;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
    
    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public void setCapacityUsed(double capacityUsed) {
        this.capacityUsed = capacityUsed;
    }

    private void addItineraryToVehicle() {
        this.vehicle.addItinerary(this);
    }

    /**
     * Get a list of customers based on the demands of the itinerary
     *
     * @return a list of customers
     */
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (PlannedDemand d : customersDemands) {
            customers.add(d.getCustomer());
        }
        return customers;
    }

    /**
     * Adds a demand to the itinerary
     *
     * @param d : planned demand to add
     * @return true if success
     */
    public boolean addDemand(PlannedDemand d) {
        if (d != null) {
            this.customersDemands.add(d);
            if (this.customersDemands.contains(d)) {
                d.setVehicleItinerary(this);
                this.addPoint(d.getCustomer());
                return true;
            }
        }
        return false;
    }
    
    public boolean checkVehicle(PlannedDemand d) {
        boolean notEnoughVehicles = false;
        double capacity = this.getVehicleCapacity();
        int totalSizeMachinesRequested = d.getTotalSizeMachines();
        if (capacityUsed + totalSizeMachinesRequested > capacity) {
            notEnoughVehicles = true;
        }
        double distanceUpdated = this.computeDistanceDemands(d);
        if (distanceUpdated > this.vehicle.getDistanceMax()) {
            notEnoughVehicles = true;
        }
        if (this.getDayNumber() < d.getFirstDay() || this.getDayNumber() > d.getLastDay()) {
            notEnoughVehicles = false;
        }
        return notEnoughVehicles;
    }

    /**
     * Assess the possibility of adding a vehicle itinerary associated to a
     * customer + Checks size of machines requested and distance to travel
     *
     * @param d : demand to assess
     * @return true if possible, false otherwise
     */
    public boolean addDemandVehicle(PlannedDemand d) {
        if (d == null) {
            return false;
        }
        double capacity = this.getVehicleCapacity();
        int totalSizeMachinesRequested = d.getTotalSizeMachines();
        if (capacityUsed + totalSizeMachinesRequested > capacity) {
            return false;
        }
        if (this.getDayNumber() < d.getFirstDay() || this.getDayNumber() > d.getLastDay()) {
            return false;
        }
        double distanceUpdated = this.computeDistanceDemands(d);
        if (distanceUpdated > this.vehicle.getDistanceMax()) {
            return false;
        }
        if (!this.addDemand(d)) {
            return false;
        }
        this.toggleDemand(d);
        double costUpdated = this.computeCostItinerary();
        this.setCost(costUpdated);
        this.setCapacityUsed(capacityUsed + totalSizeMachinesRequested);
        this.setDistanceTravelled(distanceUpdated);
        this.updateCostDay();
        return true;
    }

    /**
     * Compute the cost of an itinerary : sum of the cost of the distance
     * travelled and the cost of using the vehicle during a day
     *
     * @return double
     */
    protected double computeCostItinerary() {
        double distanceDemands = this.computeDistanceDemands(new ArrayList<>(super.getPoints()));
        double newCost = distanceDemands * this.vehicle.getDistanceCost();
        if (distanceDemands != 0.0) { // if used
            newCost += this.vehicle.getDayCost();
        }
        return newCost;
    }

    /**
     * Computes the distance between each point in a given sequence
     *
     * @param pointsItinerary : points in the sequence
     * @return the distance
     */
    protected double computeDistanceDemands(List<Point> pointsItinerary) {
        if (pointsItinerary.isEmpty()) {
            return 0.0;
        }
        double distance = this.vehicle.getDepot().getDistanceTo(pointsItinerary.get(0));
        for (int i = 1; i < pointsItinerary.size(); i++) {
            Point previousPoint = pointsItinerary.get(i - 1);
            if (!previousPoint.equals(pointsItinerary.get(i))) {
                distance += previousPoint.getDistanceTo(pointsItinerary.get(i));
            }
        }
        distance += pointsItinerary.get(pointsItinerary.size() - 1).getDistanceTo(this.vehicle.getDepot());
        return distance;
    }
    
    /**
     * Computes the distance between each point in the sequence with a
     * subsequent request
     *
     * @param d : demand to add
     * @return the distance
     */
    protected double computeDistanceDemands(PlannedDemand d) {
        List<Point> pointsItinerary = new ArrayList<>(super.getPoints());
        pointsItinerary.add(d.getCustomer());
        return this.computeDistanceDemands(pointsItinerary);
    }

    /**
     * Clears data related to the vehicle itinerary
     */
    public void clear() {
        this.capacityUsed = 0.0;
        this.distanceTravelled = 0.0;
        this.cost = 0.0;
    }
}
