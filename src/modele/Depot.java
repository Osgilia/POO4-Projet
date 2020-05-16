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
 * Point représentant le dépôt
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

    public Depot(Integer id, double x, double y) {
        super(id, 1, x, y);
        this.vehicleSet = new HashSet<>();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Depot)) {
            return false;
        }
        Depot other = (Depot) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
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
