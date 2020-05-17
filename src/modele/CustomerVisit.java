package modele;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Describes the relationship between an itinerary and a customer
 *
 * @author Henri, Lucas, Louis
 */
@Entity
public class CustomerVisit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "VISIT_ITINERARY_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    })
    private Itinerary itinerary;

    @JoinColumn(name = "VISIT_CUSTOMER_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    })
    private Customer customer;

    /**
     * No-argument constructor
     */
    public CustomerVisit() {

    }

    /**
     * Parameterized constructor
     *
     * @param itinerary
     * @param customer
     */
    public CustomerVisit(Itinerary itinerary, Customer customer) {
        this.itinerary = itinerary;
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CustomerVisit)) {
            return false;
        }
        CustomerVisit other = (CustomerVisit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modele.CustomerVisit[ id=" + id + " ]";
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public Customer getCustomer() {
        return customer;
    }

    /**
     * Assess the possibility of adding an itinerary associated to a customer
     * Checks size of machines requested and distance to travel
     *
     * @return true if possible, false otherwise
     */
//    public boolean checkCapability() {
//        if (this.itinerary instanceof VehicleItinerary) {
//            VehicleItinerary vehicleItinerary = (VehicleItinerary) this.itinerary;
//            double capacity = vehicleItinerary.getVehicleCapacity();
//            double capacityUsed = vehicleItinerary.getVehicleCapacityUsed();
////            double demandSize = this.customer.getDemandSize();
//            
//        }
//        if (this.itinerary instanceof TechnicianItinerary) {
//
//        }
//        return false;
//    }
}
