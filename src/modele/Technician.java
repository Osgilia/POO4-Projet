package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
    , @NamedQuery(name = "Technician.findByInstance", query = "SELECT t FROM Technician t WHERE t.pInstance = :instance")
})
@DiscriminatorValue("3")
public class Technician extends Point implements Serializable {

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
     * Machines the technician can install
     */
    @ManyToMany
    @JoinTable(
            name = "machineType_technician",
            joinColumns = @JoinColumn(name = "technician_id"),
            inverseJoinColumns = @JoinColumn(name = "machineType_id"))
    private Set<MachineType> authorizedMachines;

    @OneToMany(mappedBy = "itinerary",
            cascade = {
                CascadeType.PERSIST
            })
    @OrderBy("dayHorizon ASC")
    private List<TechnicianItinerary> itineraries;

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
        this.authorizedMachines = new HashSet<>();
        this.itineraries = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param id
     * @param idLocation
     * @param x
     * @param y
     * @param distanceCost
     * @param dayCost
     * @param demandMax
     * @param distanceMax
     * @param usageCost
     * @param instance
     */
    public Technician(Integer id, Integer idLocation, double x, double y, double distanceMax, int demandMax, double usageCost, double distanceCost, double dayCost, Instance instance) {
        super(id, idLocation, 3, x, y, instance);
        this.distanceCost = distanceCost;
        this.dayCost = dayCost;
        this.usageCost = usageCost;
        this.distMax = distanceMax;
        this.demandMax = demandMax;
        this.authorizedMachines = new HashSet<>();
        this.itineraries = new ArrayList<>();
    }

    @Override
    public String toString() {
        String str = "Technician (" + super.getIdLocation() + ") at " + super.toString();
        return str;
    }

    public double getDistanceCost() {
        return distanceCost;
    }

    public double getDayCost() {
        return dayCost;
    }

    public double getUsageCost() {
        return usageCost;
    }

    public double getDistMax() {
        return distMax;
    }

    public int getDemandMax() {
        return demandMax;
    }

    public void addItinerary(TechnicianItinerary t) {
        this.itineraries.add(t);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.distanceCost) ^ (Double.doubleToLongBits(this.distanceCost) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.dayCost) ^ (Double.doubleToLongBits(this.dayCost) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.usageCost) ^ (Double.doubleToLongBits(this.usageCost) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.distMax) ^ (Double.doubleToLongBits(this.distMax) >>> 32));
        hash = 41 * hash + this.demandMax;
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
        final Technician other = (Technician) obj;
        if (Double.doubleToLongBits(this.distanceCost) != Double.doubleToLongBits(other.distanceCost)) {
            return false;
        }
        if (Double.doubleToLongBits(this.dayCost) != Double.doubleToLongBits(other.dayCost)) {
            return false;
        }
        if (Double.doubleToLongBits(this.usageCost) != Double.doubleToLongBits(other.usageCost)) {
            return false;
        }
        if (this.demandMax != other.demandMax) {
            return false;
        }
        return true;
    }

    /**
     * Adds a machine to the list of machines the technician can install
     *
     * @param machine
     * @return
     */
    public boolean addAccreditation(MachineType machine) {
        if (machine != null) {
            if (this.authorizedMachines.add(machine)) {
                if (machine.addTechnician(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the technician can install the machines requested and if he can
     * work (doesn't need to rest)
     *
     * @todo : 2 days rest if 5 working days, else 1 day rest
     * @todo : to review
     * @param demand
     * @param dayNumber
     * @return true if success
     */
    public boolean canInstallDemand(PlannedDemand demand, int dayNumber) {
        boolean canInstall = false;
        for (MachineType machineType : authorizedMachines) {
            if (machineType.equals(demand.getMachine())) {
                canInstall = true;
            }
        }
        int lastDay = 0;
        int nbStraightWorkingDays = 0;
        for (TechnicianItinerary itinerary : itineraries) {
            if (itinerary.getCost() != 0.0) {
                int currentDayNumber = itinerary.getDayNumber();
                if (currentDayNumber > lastDay) {
                    if (currentDayNumber == lastDay + 1) {
                        lastDay = currentDayNumber;
                        nbStraightWorkingDays++;
                        if (nbStraightWorkingDays > 4) {
                            canInstall = false;
                        }
                    } else {
                        nbStraightWorkingDays = 0;
                    }
                }
            }
        }

        return canInstall;
    }
}
