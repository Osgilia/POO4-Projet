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
    
    @OneToMany(mappedBy = "technician_itinerary",
            cascade = {
                CascadeType.PERSIST
            })
    private List<Demand> customersDemands;

    /**
     * No-argument constructor
     */
    public TechnicianItinerary() {
        super();
        this.cost = 0.0;
        this.nbDemands = 0;
        this.distanceTravelled = 0.0;
        this.customersDemands = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param technician
     */
    public TechnicianItinerary(Technician technician) {
        super(2);
        this.technician = technician;
        this.addItineraryToVehicle();
        this.customersDemands = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TechnicianItinerary)) {
            return false;
        }
        TechnicianItinerary other = (TechnicianItinerary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "- Itinerary of " + technician + " [Nb demands = " + nbDemands + "/" + this.technician.getDemandMax() + " | Distance = "
                + distanceTravelled + "/" + this.technician.getDistMax() + " | Cost = " + cost + "] :";
        for (Demand demand : customersDemands) {
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

    private void addItineraryToVehicle() {
        this.technician.addItinerary(this);
    }
    
    /**
     * Get a list of customers based on the demands of the itinerary
     *
     * @return a list of customers
     */
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        for(Demand d : customersDemands) {
            customers.add(d.getCustomer());
        }
        return customers;
    }
    
    /**
     * Adds a demand to the itinerary
     *
     * @param d : demand to add
     * @return true if success
     */
    public boolean addDemand(Demand d) {
        if (d != null) {
            this.customersDemands.add(d);
            if (this.customersDemands.contains(d)) {
                this.toggleDemand(d);
                d.setTechnicianItinerary(this);
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
    public boolean addDemandTechnician(Demand d) {
        if (d == null) {
            return false;
        }
        int dayNumber = this.getDayNumber();
        if (dayNumber < d.getFirstDay() || this.getDayNumber() > d.getLastDay()) {
            return false;
        }
        int deliveryDayNumber = d.getVehicleItinerary().getDayNumber();
        if (dayNumber <= deliveryDayNumber) { // installation possible the day after the delivery
            return false;
        }
        if (nbDemands + 1 > this.technician.getDemandMax()) {
            return false;
        }
        double distanceUpdated = this.computeDistanceDemands();
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
     * Compute the distance between each customer in the sequence
     *
     * @return double : distance travelled in the itinerary
     */
    protected double computeDistanceDemands() {
        List<Customer> customers = this.getCustomers();
        if (customers.isEmpty()) {
            return 0.0;
        }
        double distance = this.technician.getDistanceTo(customers.get(0));
        for (int i = 1; i < customers.size(); i++) {
            Customer prevCustomer = customers.get(i - 1);
            if (!prevCustomer.equals(customers.get(i))) {
                distance += prevCustomer.getDistanceTo(customers.get(i));
            }
        }
        distance += customers.get(customers.size() - 1).getDistanceTo(this.technician);
        return distance;
    }

    /**
     * Compute the cost of an itinerary : sum of the cost of the distance
     * travelled, the cost of using the technician during a day
     *
     * @return double : the cost of the itinerary
     */
    protected double computeCostItinerary() {
        double distanceDemands = this.computeDistanceDemands();
        double newCost = distanceDemands * this.technician.getDistanceCost();
        if (distanceDemands != 0.0) { // if used
            newCost += this.technician.getDayCost();
        }
        return newCost;
    }
}
