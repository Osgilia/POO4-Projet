package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    , @NamedQuery(name = "Instance.findByNom", query = "SELECT i FROM Instance i WHERE i.nom = :nom")})
public class Instance implements Serializable {

    /************************
     *      ATTRIBUTES      *
     ***********************/

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
    
    @OneToMany(mappedBy = "ninstance")
    private List<Planning> planningList;
    
    /**
     * Vehicle(s) affected to this instance
     */
    @OneToMany(mappedBy = "vInstance")
    private List<Vehicle> vehicleList;
    
    /**
     * Point(s) affected to this instance
     */
    @OneToMany(mappedBy = "pInstance")
    private List<Point> pointList;
    
    /**
     * Machine(s) affected to this instance
     */
    @OneToMany(mappedBy = "mInstance")
    private List<Machine> machineList;
    
    /****************************
    *       CONSTRUCTORS        *
    ****************************/
    
    public Instance() {
        this.name = "DEFAULT NAME";
        this.dataset = "DEFAULT NAME";
        this.planningList = new ArrayList<>();
        this.vehicleList = new ArrayList<>();
        this.pointList = new ArrayList<>();
        this.machineList = new ArrayList<>();
    }

    public Instance(String name, String dataset) {
        this();
        this.name = name;
        this.dataset = dataset;
        this.planningList = new ArrayList<>();
        this.vehicleList = new ArrayList<>();
        this.pointList = new ArrayList<>();
        this.machineList = new ArrayList<>();
    }
    
    
    /********************************
     *      GETTERS & SETTERS       *
     *******************************/

    public String getName() {
        return name;
    }

    public String getDataset() {
        return dataset;
    }

    
    /************************
     *       METHODS        *
     ***********************/

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
        return "Instance : " + " name = " + name + ", dataset = " + dataset + ']';
    }
    
    /**
     * Adds a planning that represents one of the solutions of the instance
     * @param p : planning
     * @return true if success
     */
    public boolean addPlanning(Planning p) {
        if(p != null) {
            if(this.planningList.add(p)) {
                p.setNinstance(this);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add a vehicle that represents one of the vehicles of this instance
     * @param v : vehicle
     * @return true if success
     */
    public boolean addVehicle(Vehicle v) {
        if(v != null) {
            if(this.vehicleList.add(v)) {
                v.setvInstance(this);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add a point that represents one of the points of this instance
     * @param p : point
     * @return true if success
     */
    public boolean addPoint(Point p) {
        if(p != null) {
            if(this.pointList.add(p)) {
                p.setpInstance(this);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add a machine that represents one of the machines of this instance
     * @param m : Machine
     * @return true if success
     */
    public boolean addMachine(Machine m) {
        if(m != null) {
            if(this.machineList.add(m)) {
                m.setmInstance(this);
                return true;
            }
        }
        return false;
    }

}
