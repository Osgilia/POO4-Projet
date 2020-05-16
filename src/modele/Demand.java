/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
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
 *
 * @author Osgilia
 */
@Entity
@Table(name = "DEMAND")
@XmlRootElement
@Inheritance(strategy = InheritanceType.JOINED)

public class Demand implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int firstDay;
    private int lastDay;
    @JoinColumn(name = "NCUSTOMER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "machine")
    private Set<OrderMachine> machinesOrdered;

    public Demand() {
        this.firstDay = 0;
        this.lastDay = 0;
        this.customer = null;
    }

    public Demand(int firstDay, int lastDay, Customer customer) {
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Demand)) {
            return false;
        }
        Demand other = (Demand) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean addMachineOrder(Machine machine, int nbMachines) {
        if (machine != null && nbMachines > 0) {
            OrderMachine order = new OrderMachine(this, machine, nbMachines);

            this.machinesOrdered.add(order);
            if (machine.addOrder(order))
            {
                return true;
            }

        }
        return false;
    }

    @Override
    public String toString() {
        return "modele.Demand[ id=" + id + " ]";
    }

}
