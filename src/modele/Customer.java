package modele;

import dao.DemandDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Point that represents a customer
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id")
    , @NamedQuery(name = "Customer.findByInstance", query = "SELECT c FROM Customer c WHERE c.pInstance = :instance")
    , @NamedQuery(name = "Customer.findByInstanceCustomer", query = "SELECT c FROM Customer c WHERE c.pInstance = :instance AND c.id = :id")
}
)
@DiscriminatorValue("2")
public class Customer extends Point implements Serializable {

    /**
     * No-argument constructor
     */
    public Customer() {
        super();
    }

    /**
     * Parameterized constructor
     *
     * @param id
     * @param idLocation
     * @param x
     * @param y
     * @param instance
     */
    public Customer(Integer id, Integer idLocation, double x, double y, Instance instance) {
        super(id, idLocation, 2, x, y, instance);
    }

    @Override
    public String toString() {
        String str = "Customer (" + super.getId() + ")" + " at " + super.toString() + " asking for :\n";
        return str;
    }
}
