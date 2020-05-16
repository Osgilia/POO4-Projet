/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a vehicle
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Table(name = "VEHICLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicle.findAll", query = "SELECT v FROM Vehicle v")
    , @NamedQuery(name = "Vehicle.findById", query = "SELECT v FROM Vehicle v WHERE v.id = :id")
    , @NamedQuery(name = "Vehicle.findByCout", query = "SELECT v FROM Vehicle v WHERE v.cout = :cout")
    , @NamedQuery(name = "Vehicle.findByCapaciteutilisee", query = "SELECT v FROM Vehicle v WHERE v.capaciteutilisee = :capaciteutilisee")
    , @NamedQuery(name = "Vehicle.findAllNotUsed", query = "SELECT v FROM Vehicle v WHERE v.listCustomer IS EMPTY")
    , @NamedQuery(name = "Vehicle.findByCapacite", query = "SELECT v FROM Vehicle v WHERE v.capacite = :capacite")})
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "COST")
    private double usageCost;

    @Column(name = "CAPACITYUSED")
    private double capacityUsed;

    @Column(name = "CAPACITY")
    private double capacity;

    @Column(name = "DISTANCEMAX")
    private double distanceMax;

    @Column(name = "DISTANCECOST")
    private double distanceCost;

    @Column(name = "DAYCOST")
    private double dayCost;

    @JoinColumn(name = "DEPOT", referencedColumnName = "ID")
    @ManyToOne
    private Depot depot;

    /**
     * No-argument constructor
     */
    public Vehicle() {
        this.capacity = 0;
        this.capacityUsed = 0;
        this.usageCost = 0.0;
        this.distanceMax = 0.0;
        this.distanceCost = 0.0;
        this.dayCost = 0.0;
    }

    /**
     * Parameterized constructor
     * @param depot
     * @param capacity
     * @param distanceMax
     * @param distanceCost
     * @param dayCost
     * @param usageCost 
     */
    public Vehicle(Integer id, Depot depot, double capacity, double distanceMax, double distanceCost, double dayCost, double usageCost) {
        this();
        this.id = id;
        this.capacity = capacity > 0 ? capacity : 0;
        this.distanceMax = distanceMax > 0 ? distanceMax : 0.0;
        this.distanceCost = distanceCost > 0 ? distanceCost : 0.0;
        this.dayCost = dayCost > 0 ? dayCost : 0.0;
        this.depot = depot;
    }

    /**
     * Copy constructor
     * @param v 
     */
    public Vehicle(Vehicle v) {
        this();
        this.capacity = v.getCapacity();
        this.depot = v.getDepot();
        this.capacityUsed = v.getCapacityUsed();
        this.usageCost = v.getUsageCost();
        this.dayCost = v.getDayCost();
        this.distanceCost = v.getDistanceCost();
        this.distanceMax = v.getDistanceMax();
    }

    public double getUsageCost() {
        return usageCost;
    }

    public double getDistanceMax() {
        return distanceMax;
    }

    public double getDistanceCost() {
        return distanceCost;
    }

    public double getDayCost() {
        return dayCost;
    }

    public double getCapacityUsed() {
        return capacityUsed;
    }

    public double getCapacity() {
        return capacity;
    }

    public Depot getDepot() {
        return depot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vehicule : id " + id + " | Capacity " + this.capacityUsed + "/" + this.capacity + " that costs " + this.usageCost
                + "\n\t\tDepot : " + this.depot;
    }

    /**
     * Clears data related to the vehicule
     */
    public void clear() {
        this.capacityUsed = 0;
    }

}
