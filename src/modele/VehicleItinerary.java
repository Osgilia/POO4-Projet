package modele;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
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

    /**
     * No-argument constructor
     */
    public VehicleItinerary() {
        super();
    }

    /**
     * Parameterized constructor
     *
     * @param vehicle
     */
    public VehicleItinerary(Vehicle vehicle) {
        super(1);
        this.vehicle = vehicle;
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
        return "- Itinerary of " + vehicle + " :" + super.toString();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getVehicleCapacity() {
        return vehicle.getCapacity();
    }

    public double getVehicleCapacityUsed() {
        return vehicle.getCapacityUsed();
    }

    public void setVehicleCapacityUsed(double newCapacity) {
        vehicle.setCapacityUsed(newCapacity);
    }

    /**
     * Assess the possibility of adding a vehicle itinerary associated to a
     * customer + Checks size of machines requested and distance to travel
     *
     * @param d : demand to assess
     * @return true if possible, false otherwise
     */
    public boolean addDemandVehicle(Demand d) {
        if (d == null) {
            return false;
        }
        double capacity = this.getVehicleCapacity();
        double capacityUsed = this.getVehicleCapacityUsed();
        int totalSizeMachinesRequested = d.getTotalSizeMachines();
        if (capacityUsed + totalSizeMachinesRequested > capacity) {
            return false;
        }
        if (this.getDayNumber() < d.getFirstDay() || this.getDayNumber() > d.getLastDay()) {
            return false;
        }
        if (!super.addDemand(d)) {
            return false;
        }
        this.setVehicleCapacityUsed(capacityUsed + totalSizeMachinesRequested);

        // todo update cost vehicle + planning
        return true;
    }
}
