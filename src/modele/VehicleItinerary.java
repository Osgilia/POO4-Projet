package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
@DiscriminatorValue("1")
public class VehicleItinerary extends Itinerary implements Serializable {

    @ManyToOne
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;

    @Column(name = "COST")
    private double cost;

    @Column(name = "DISTANCETRAVELLED")
    private double distanceTravelled;

    @Column(name = "CAPACITYUSED")
    private double capacityUsed;

    @OneToMany(mappedBy = "vehicleItinerary",
            cascade = {
                CascadeType.MERGE
            })
    @OrderBy("positionVehicle ASC")
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
        final VehicleItinerary other = (VehicleItinerary) obj;
        if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
            return false;
        }
        if (Double.doubleToLongBits(this.distanceTravelled) != Double.doubleToLongBits(other.distanceTravelled)) {
            return false;
        }
        if (Double.doubleToLongBits(this.capacityUsed) != Double.doubleToLongBits(other.capacityUsed)) {
            return false;
        }
        if (!Objects.equals(this.vehicle, other.vehicle)) {
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

    public List<PlannedDemand> getCustomersDemands() {
        return new ArrayList<>(customersDemands);
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
            this.customersDemands.add(this.customersDemands.size(), d);
            if (this.customersDemands.contains(d)) {
                this.toggleDemand(d);
                d.setPositionVehicle(this.customersDemands.size());
                d.setVehicleItinerary(this);
                this.addPoint(d.getCustomer());
                return true;
            }
        }
        return false;
    }

    /**
     * Preliminary check of the vehicle depending on the inserted demand
     *
     * @param d : planned demand
     * @return boolean
     */
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
    protected double computeDistanceDemands(List<ItineraryPoint> pointsItinerary) {
        if (pointsItinerary.isEmpty()) {
            return 0.0;
        }
        this.vehicle.getDepot().addDestination(pointsItinerary.get(0).getPoint(), this.vehicle.getDepot().computeDistance(pointsItinerary.get(0).getPoint()));
        double distance = this.vehicle.getDepot().getDistanceTo(pointsItinerary.get(0).getPoint());
        for (int i = 1; i < pointsItinerary.size(); i++) {
            Point previousPoint = pointsItinerary.get(i - 1).getPoint();
            if (!previousPoint.equals(pointsItinerary.get(i))) {
                previousPoint.addDestination(pointsItinerary.get(i).getPoint(), previousPoint.computeDistance(pointsItinerary.get(i).getPoint()));
                distance += previousPoint.getDistanceTo(pointsItinerary.get(i).getPoint());
            }
        }
        pointsItinerary.get(pointsItinerary.size() - 1).getPoint().addDestination(this.vehicle.getDepot(), pointsItinerary.get(pointsItinerary.size() - 1).getPoint().computeDistance(this.vehicle.getDepot()));
        distance += pointsItinerary.get(pointsItinerary.size() - 1).getPoint().getDistanceTo(this.vehicle.getDepot());
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
        List<ItineraryPoint> pointsItinerary = new ArrayList<>(super.getPoints());
        pointsItinerary.add(new ItineraryPoint(this, d.getCustomer(), pointsItinerary.size()));
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
