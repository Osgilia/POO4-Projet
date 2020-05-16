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
import javax.persistence.OneToMany;

/**
 *
 * @author Osgilia
 */
@Entity
public class Machine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int size;
    private double penality;

    public Long getId() {
        return id;
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "machine")
    private Set<OrderMachine> demands;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Set<OrderMachine> getDemands() {
        return demands;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Machine)) {
            return false;
        }
        Machine other = (Machine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean addOrder(OrderMachine order) {
        if (order != null && order.getMachine() == this) {
            if (!this.demands.contains(order)) {
                this.demands.add(order);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "modele.Machine[ id=" + id + " ]";
    }

}
