package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
 * Represents an ordered sequence in which customers have their machines
 * installed
 *
 * @author Lucas, Louis, Henri
 */
@Entity
@Table(name = "TECHNICIANITINERARY")
@XmlRootElement
@NamedQueries({})
@DiscriminatorValue("2")
public class TechnicianItinerary extends Itinerary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "TECHNICIAN_ID")
    private Technician technician;

    @Column(name = "COST")
    private double cost;

    @Column(name = "DISTANCETRAVELLED")
    private double distanceTravelled;

    @Column(name = "NBDEMANDS")
    private int nbDemands;

    @OneToMany(mappedBy = "technicianItinerary",
            cascade = {
                CascadeType.MERGE
            })
    private Set<PlannedDemand> customersDemands;

    /**
     * No-argument constructor
     */
    public TechnicianItinerary() {
        super();
        this.cost = 0.0;
        this.nbDemands = 0;
        this.distanceTravelled = 0.0;
        this.customersDemands = new HashSet<>();
    }

    /**
     * Parameterized constructor
     *
     * @param technician
     */
    public TechnicianItinerary(Technician technician) {
        super(2);
        this.technician = technician;
        this.addItineraryToTechnician();
        this.customersDemands = new HashSet<>();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.technician);
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
        final TechnicianItinerary other = (TechnicianItinerary) obj;
        if (!Objects.equals(this.technician, other.technician)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "- Itinerary of " + technician + " [Nb demands = " + nbDemands + "/" + this.technician.getDemandMax() + " | Distance = "
                + distanceTravelled + "/" + this.technician.getDistMax() + " | Cost = " + cost + "] :";
        for (PlannedDemand demand : customersDemands) {
            str += "\n\t\t\t\t " + demand;
        }
        return str;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public void setNbDemands(int nbDemands) {
        this.nbDemands = nbDemands;
    }

    public double getCost() {
        return cost;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public int getNbDemands() {
        return nbDemands;
    }

    public Set<PlannedDemand> getCustomersDemands() {
        return customersDemands;
    }

    private void addItineraryToTechnician() {
        this.technician.addItinerary(this);
    }

    /**
     * Adds a demand to the itinerary
     *
     * @param d : demand to add
     * @return true if success
     */
    public boolean addDemand(PlannedDemand d) {
        if (d != null) {
            this.customersDemands.add(d);
            if (this.customersDemands.contains(d)) {
                this.toggleDemand(d);
                d.setTechnicianItinerary(this);
                this.addPoint(d.getCustomer());
                return true;
            }
        }
        return false;
    }

    /**
     * Assess the possibility of adding a technician itinerary associated to a
     * customer
     *
     * @param d : demand to assess
     * @return true if possible, false otherwise
     */
    public boolean addDemandTechnician(PlannedDemand d) {
        if (d == null) {
            return false;
        }
        int dayNumber = this.getDayNumber();
        if (dayNumber < d.getFirstDay() || dayNumber > d.getLastDay()) {
            return false;
        }
        int deliveryDayNumber = d.getVehicleItinerary().getDayNumber();
        if (dayNumber <= deliveryDayNumber) { // installation possible the day after the delivery
            return false;
        }
        if (nbDemands + 1 > this.technician.getDemandMax()) {
            return false;
        }
        double distanceUpdated = this.computeDistanceDemands(d);
        if (distanceUpdated > this.technician.getDistMax()) {
            return false;
        }
        if (!this.technician.canInstallDemand(d, dayNumber)) {
            return false;
        }
        if (!this.addDemand(d)) {
            return false;
        }
        double costUpdated = this.computeCostItinerary();
        this.setCost(costUpdated);
        this.setNbDemands(this.getNbDemands() + 1);
        this.setDistanceTravelled(distanceUpdated);
        this.updateCostDay();
        return true;
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
        double distance = this.technician.getDistanceTo(pointsItinerary.get(0));
        for (int i = 1; i < pointsItinerary.size(); i++) {
            Point previousPoint = pointsItinerary.get(i - 1);
            if (!previousPoint.equals(pointsItinerary.get(i))) {
                distance += previousPoint.getDistanceTo(pointsItinerary.get(i));
            }
        }
        if (pointsItinerary.size() == 1) {
            distance += distance;
        } else {
            distance += pointsItinerary.get(pointsItinerary.size() - 1).getDistanceTo(this.technician);
        }

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
     * Compute the cost of an itinerary : sum of the cost of the distance
     * travelled, the cost of using the technician during a day
     *
     * @return double : the cost of the itinerary
     */
    protected double computeCostItinerary() {
        double distanceDemands = this.computeDistanceDemands(new ArrayList<>(super.getPoints()));
        double newCost = distanceDemands * this.technician.getDistanceCost();
        if (distanceDemands != 0.0) { // if used
            newCost += this.technician.getDayCost();
        }
        return newCost;
    }
}
