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

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "customer")
    private List<Demand> customerDemands;

    /**
     * No-argument constructor
     */
    public Customer() {
        super();
        this.customerDemands = new ArrayList<>();
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
        this.customerDemands = new ArrayList<>();
    }

    @Override
    public String toString() {
        String str = "Customer (" + super.getId() + ")" + " at " + super.toString() + " asking for :\n";
        for (Demand d : customerDemands) {
            str += "\t\t\t\t" + d.toString() + "\n";
        }
        return str;
    }

    public List<Demand> getCustomerDemands() {
        return customerDemands;
    }

    /**
     * Adds a demand
     *
     * @param id
     * @param firstDay : first day of delivery window
     * @param lastDay : last day of delivery window
     * @param m : machine to add
     * @param nbMachines
     * @param demandManager : Dao demand
     * @return true if demand is added
     */
    public boolean addDemand(int id, int firstDay, int lastDay, MachineType m, int nbMachines, DemandDao demandManager) {
        if (firstDay <= lastDay) {
            Demand d = new Demand(id, firstDay, lastDay, this, m, nbMachines);
//            System.out.println("m = "+m.toString());
            m.addDemand(d);
            this.customerDemands.add(d);
            if (this.customerDemands.contains(d)) {
                return true;
            }
            demandManager.create(d);
        }
        return false;
    }

    /**
     * Clears data related to the customer
     */
    public void clear() {
        for (Demand d : this.customerDemands) {
            d.clear();
        }
        this.customerDemands.clear();
    }
}
