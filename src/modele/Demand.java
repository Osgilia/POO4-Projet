/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.Objects;
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
    private Long id;

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
    private Machine machine;

    @Column(name = "NBMACHINES")
    private int nbMachines;

    /**
     * No-argument constructor
     */
    public Demand() {
        this.firstDay = 0;
        this.lastDay = 0;
        this.customer = null;
        this.machine = null;
        this.nbMachines = 0;
    }

    /**
     * Parameterized constructor
     * @param firstDay
     * @param lastDay
     * @param customer
     * @param machine
     * @param nbMachines 
     */
    public Demand(int firstDay, int lastDay, Customer customer, Machine machine, int nbMachines) {
        this();
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.customer = customer;
        this.nbMachines = nbMachines;
        this.machine = machine;
    }

    public Long getId() {
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
        return "- " + nbMachines + " machines of type " + machine;
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

    /**
     * Adds a machine order
     *
     * @param machine : machine type to order
     * @param nbMachines : number of machines
     * @return
     */
//    public boolean addMachineOrder(Machine machine, int nbMachines) {
//        if (machine != null && nbMachines > 0) {
//            OrderMachine order = new OrderMachine(this, machine, nbMachines);
//            this.machineOrders.add(order);
//            if (machine.addOrder(order)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
