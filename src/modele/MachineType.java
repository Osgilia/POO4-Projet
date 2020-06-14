package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Class that represents a type of machine
 *
 * @author Henri, Lucas, Louis
 */
@Entity
public class MachineType implements Serializable {

    /************************
     *      ATTRIBUTES      *
     ***********************/

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     /**
     * Id of a machine of this type for a specific instance
     */
    @Column(name = "IDMACHINE")
    private int idMachine;
    
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
     * Technicians that can install the machine
     */
    @ManyToMany(mappedBy = "authorizedMachines")
    private Set<Technician> certifiedTechnicians;

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
    public MachineType() {
        this.idMachine = 0;
        this.size = 0;
        this.penalty = 0.0;
        this.certifiedTechnicians = new HashSet<>();
        this.demandsMachineType = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param id
     * @param size
     * @param penalty
     * @param instance
     */
    public MachineType(Integer id, int size, double penalty, Instance instance) {
        this();
        this.idMachine = id;
        this.size = size;
        this.penalty = penalty;
        this.mInstance = instance;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /************************
     *       METHODS        *
     ***********************/
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
        final MachineType other = (MachineType) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[size = " + size + " and penalty = " + penalty + "]";
    }

    public Integer getIdMachine() {
        return idMachine;
    }
    
    
    
    /**
     * Adds a technician to the list of technicians that can install this machine
     *
     * @param technician
     * @return true if success
     */
    public boolean addTechnician(Technician technician) {
        if (technician != null) {
            return this.certifiedTechnicians.add(technician);
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
