package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Class that represents a type of machine
 *
 * @author Henri, Lucas, Louis
 */
@Entity
public class Machine implements Serializable {

    /************************
     *      ATTRIBUTES      *
     ***********************/

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Size of a machine of this type
     */
    @Column(name = "SIZE")
    private int size;

    /**
     * Penalty applied each day a machine of this type is used
     */
    @Column(name = "PENALTY")
    private double penalty;

    /**
     * Potential machines the technician can install
     */
    @OneToMany(mappedBy = "machine",
            cascade = {
                CascadeType.PERSIST
            })
    private List<Installation> potentialInstallations;

    /**
     * Demands associated to the machine type
     */
    @OneToMany(mappedBy = "machine",
            cascade = {
                CascadeType.PERSIST
            })
    private List<Demand> demandsMachineType;

    /**
     * Instance using this machine
     */
    @JoinColumn(name = "MINSTANCE", referencedColumnName = "ID")
    @ManyToOne
    private Instance mInstance;
    
    /****************************
    *       CONSTRUCTORS        *
    ****************************/

    /**
     * No-argument constructor
     */
    public Machine() {
        this.size = 0;
        this.penalty = 0.0;
        this.potentialInstallations = new ArrayList<>();
        this.demandsMachineType = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param id
     * @param size
     * @param penalty
     */
    public Machine(Integer id, int size, double penalty) {
        this();
        this.id = id;
        this.size = size;
        this.penalty = penalty;
    }
    
    
    /********************************
     *      GETTERS & SETTERS       *
     *******************************/

    public int getSize() {
        return size;
    }

    public double getPenalty() {
        return penalty;
    }

    public Instance getmInstance() {
        return mInstance;
    }

    public void setmInstance(Instance mInstance) {
        this.mInstance = mInstance;
    }
    
    
    /************************
     *       METHODS        *
     ***********************/

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Machine other = (Machine) obj;
        if (this.size != other.size) {
            return false;
        }
        if (Double.doubleToLongBits(this.penalty) != Double.doubleToLongBits(other.penalty)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[size = " + size + " and penalty = " + penalty + "]";
    }

    

    /**
     * Adds a relation entity between this machine and a technician
     *
     * @param installation : relation entity
     * @return true if success
     */
    public boolean addTechnician(Installation installation) {
        if (installation != null && installation.getMachine() == this) {
            if (!this.potentialInstallations.contains(installation)) {
                this.potentialInstallations.add(installation);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a demand associated to this type of machine
     *
     * @param demand
     * @return true if success
     */
    public boolean addDemand(Demand demand) {
        if (demand != null) {
            this.demandsMachineType.add(demand);
            if (this.demandsMachineType.contains(demand)) {
                return true;
            }
        }
        return false;
    }
}
