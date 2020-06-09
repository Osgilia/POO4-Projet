package modele;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Point that represents a depot
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "DEPOT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Depot.findAll", query = "SELECT d FROM Depot d")
    , @NamedQuery(name = "Depot.findById", query = "SELECT d FROM Depot d WHERE d.id = :id")})
@DiscriminatorValue("1")
public class Depot extends Point implements Serializable {

    @OneToMany(mappedBy = "ndepot")
    private Set<Vehicle> vehicleSet;

    public Depot() {
        super();
        this.vehicleSet = new HashSet<>();
    }

    public Depot(Integer id, Integer idLocation, double x, double y, Instance instance) {
        super(id, idLocation, 1, x, y, instance);
        this.vehicleSet = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Depot : " + super.toString();
    }

    /**
     * Adds a vehicle to the depot
     *
     * @param v : vehicle to add
     * @return true if success
     */
    /*public boolean addVehicle(Vehicle v) {
        if (v != null) {
            if (this.vehicleSet.add(v)) {
                v.setDepot(this);
                return true;
            }
        }
        return false;
    }*/
}
