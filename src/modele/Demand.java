package modele;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a request from a customer
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "DEMAND")
@XmlRootElement
@Inheritance(strategy = InheritanceType.JOINED)
public class Demand implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * First day of the delivery window
     */
    private int firstDay;

    /**
     * Last day of the delivery window
     */
    private int lastDay;

    @JoinColumn(name = "NCUSTOMER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Customer customer;

    @JoinColumn(name = "NMACHINE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MachineType machine;

    @Column(name = "NBMACHINES")
    private int nbMachines;

    /**
     * Demands that are linked to a planning
     */
    @OneToMany(mappedBy = "demand",
            cascade = {
                CascadeType.PERSIST
            })
    private Set<PlannedDemand> plannedDemands;

    /**
     * No-argument constructor
     */
    public Demand() {
        this.firstDay = 0;
        this.lastDay = 0;
        this.customer = null;
        this.machine = null;
        this.nbMachines = 0;
        this.plannedDemands = new HashSet<>();
    }

    /**
     * Parameterized constructor
     *
     * @param id
     * @param firstDay
     * @param lastDay
     * @param customer
     * @param machine
     * @param nbMachines
     */
    public Demand(int id, int firstDay, int lastDay, Customer customer, MachineType machine, int nbMachines) {
        this();
        this.id = id;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.customer = customer;
        this.nbMachines = nbMachines;
        this.machine = machine;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Demand other = (Demand) obj;
        if (this.firstDay != other.firstDay) {
            return false;
        }
        if (this.lastDay != other.lastDay) {
            return false;
        }
        if (this.nbMachines != other.nbMachines) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.machine, other.machine)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + id + "] : " + nbMachines + " machine(s) of type " + machine;
    }

    public int getFirstDay() {
        return firstDay;
    }

    public int getLastDay() {
        return lastDay;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MachineType getMachine() {
        return machine;
    }
    
    /**
     * Adds a demand in the list of demands of this type that are associated to
     * a planning
     *
     * @param plannedDemand
     * @return true if success
     */
    public boolean addPlannedDemand(PlannedDemand plannedDemand) {
        if (plannedDemand != null && plannedDemand.getDemand().equals(this)) {
            return this.plannedDemands.add(plannedDemand);
        }
        return false;
    }

    /**
     * Returns the total size of the requested machine(s)
     *
     * @return integer
     */
    public int getTotalSizeMachines() {
        return this.machine.getSize() * this.nbMachines;
    }

    /**
     * Clears data related to the request
     */
    public void clear() {
        this.customer = null;
        this.nbMachines = 0;
        this.firstDay = 0;
        this.lastDay = 0;
        this.machine = null;
    }
}
