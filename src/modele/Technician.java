package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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
 * Point thats represents a technician
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "TECHNICIAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Technician.findAll", query = "SELECT t FROM Technician t")
})
@DiscriminatorValue("3")
public class Technician extends Point implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Cost per distance unit covered by the technician
     */
    @Column(name = "DISTANCECOST")
    private double distanceCost;

    /**
     * Daily cost of the technician
     */
    @Column(name = "DAYCOST")
    private double dayCost;

    /**
     * Cost of using a technician during a day of the planning horizon
     */
    @Column(name = "USAGECOST")
    private double usageCost;

    /**
     * Maximum distance during a day of work
     */
    @Column(name = "DISTMAX")
    private double distMax;

    /**
     * Maximum amount of demands during a day of work
     */
    @Column(name = "DEMANDMAX")
    private int demandMax;

    /**
     * Potential machines the technician can install
     */
    @OneToMany(mappedBy = "technician",
            cascade = {
                CascadeType.PERSIST
            })
    private List<Installation> potentialInstallations;

    /**
     * No-argument constructor
     */
    public Technician() {
        super();
        this.distanceCost = 0.0;
        this.dayCost = 0.0;
        this.usageCost = 0.0;
        this.distMax = 0.0;
        this.demandMax = 0;
        this.potentialInstallations = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param id
     * @param x
     * @param y
     * @param distanceCost
     * @param dayCost
     * @param demandMax
     * @param distanceMax
     * @param usageCost
     */
    public Technician(Integer id, double x, double y, double distanceMax, int demandMax, double usageCost, double distanceCost, double dayCost) {
        super(id, 3, x, y);
        this.id = id;
        this.distanceCost = distanceCost;
        this.dayCost = dayCost;
        this.usageCost = usageCost;
        this.distMax = distanceMax;
        this.demandMax = demandMax;
        this.potentialInstallations = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Technician)) {
            return false;
        }
        Technician other = (Technician) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Technician (" + id + ") " + " [distance cost = " + distanceCost + ", day cost = "
                + dayCost + ", usage cost = " + usageCost + " max distance = "
                + distMax + ", max demands = " + demandMax + ']';
    }

    /**
     * Adds a relation entity between this technician and a machine
     *
     * @param machine
     * @return
     */
    public boolean addPotentialInstallation(Machine machine) {
        if (machine != null) {
            Installation installation = new Installation(machine, this);
            if (!this.potentialInstallations.contains(installation)) {
                this.potentialInstallations.add(installation);
                if (machine.addTechnician(installation)) {
                    return true;
                }
            }
        }
        return false;
    }
}
