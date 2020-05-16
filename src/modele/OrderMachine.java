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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Osgilia
 */
@Entity
public class OrderMachine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "DEMAND", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Demand demand;
    @JoinColumn(name = "MACHINE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Machine machine;
    private int nbMachines;

    public OrderMachine() {
        this.demand = null;
        this.machine = null;
        this.nbMachines = 0;
    }

    public OrderMachine(Demand demand, Machine machine, int nbMachines) {
        this.demand = demand;
        this.machine = machine;
        this.nbMachines = nbMachines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Machine getMachine() {
        return machine;
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
        if (!(object instanceof OrderMachine)) {
            return false;
        }
        OrderMachine other = (OrderMachine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modele.OrderMachine[ id=" + id + " ]";
    }

}
