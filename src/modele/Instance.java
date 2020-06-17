package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Instance associated to one or more plannings
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "INSTANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instance.findAll", query = "SELECT i FROM Instance i")
    , @NamedQuery(name = "Instance.findById", query = "SELECT i FROM Instance i WHERE i.id = :id")
    , @NamedQuery(name = "Instance.findByName", query = "SELECT i FROM Instance i WHERE i.name = :name")
    , @NamedQuery(name = "Instance.findByDataset", query = "SELECT i FROM Instance i WHERE i.dataset = :dataset")
    , @NamedQuery(name = "Instance.findByNameAndDataSet", query = "SELECT i FROM Instance i WHERE i.dataset = :dataset AND i.name = :name")})
public class Instance implements Serializable {

    /**
     * **********************
     * ATTRIBUTES * *********************
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    @Basic(optional = false)
    @Column(name = "DATASET")
    private String dataset;

    @Basic(optional = false)
    @Column(name = "NBDAYS")
    private int nbDays;

    @OneToMany(mappedBy = "ninstance")
    private List<Planning> planningList;

    /**
     * Vehicle affected to this instance
     */
    @OneToOne(mappedBy = "vInstance")
    private Vehicle vehicle;

    /**
     * MachineType(s) affected to this instance
     */
    @OneToMany(mappedBy = "mInstance")
    private Set<MachineType> machineList;

    /**
     * **************************
     * CONSTRUCTORS * **************************
     */
    public Instance() {
        this.name = "DEFAULT NAME";
        this.dataset = "DEFAULT NAME";
        this.nbDays = 0;
        this.planningList = new ArrayList<>();
        this.machineList = new HashSet<>();
    }

    public Instance(String name, String dataset, int nbDays) {
        this();
        this.name = name;
        this.dataset = dataset;
        this.nbDays = nbDays;
        this.planningList = new ArrayList<>();
        this.machineList = new HashSet<>();
    }

    /**
     * ******************************
     * GETTERS & SETTERS * *****************************
     */
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDataset() {
        return dataset;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getNbDays() {
        return nbDays;
    }

    /**
     * **********************
     * METHODS * *********************
     */
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
        final Instance other = (Instance) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.dataset, other.dataset)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "\nInstance : " + name + " from " + dataset + "\n";
        str += "\nMachines :";
        for (MachineType m : machineList) {
            str += "\n\t" + m;
        }
        return str;
    }

    /**
     * Gets the last planning of the instance which has not been sequenced yet
     *
     * @return the last planning
     */
    public Planning getLastPlanning() {
        for (Planning p : planningList) {
            if (p.getCost() == 0) {
                return p;
            }
        }
        return null;
    }

    /**
     * Gets machine entity from its id
     *
     * @param id
     * @return MachineType
     */
    public MachineType getMachineType(int id) {
        for (MachineType m : machineList) {
            if (m.getIdMachine() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Add a machine that represents one of the machines of this instance
     *
     * @param m : MachineType
     * @return true if success
     */
    public boolean addMachine(MachineType m) {
        if (m != null) {
            if (this.machineList.add(m)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a planning that represents one of the solutions of the instance
     *
     * @param p : planning
     * @return true if success
     */
    public boolean addPlanning(Planning p) {
        if (p != null) {
            if (this.planningList.add(p)) {
                return true;
            }
        }
        return false;
    }
}
