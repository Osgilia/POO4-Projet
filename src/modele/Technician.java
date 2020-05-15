/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osgilia
 */
@Entity
@Table(name = "TECHNICIAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Technician.findAll", query = "SELECT t FROM Technician t")
}
)
@DiscriminatorValue("3")
public class Technician extends Point implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic(optional = false)
    @Column(name = "DEMAND")
    private double distanceCost;
    private double dayCost;
    private double cost;
    private double distMax;
    private int demandMax;

    public Technician() {
        super();
        this.distanceCost = 0;
        this.dayCost = 0;
        this.cost = 0;
        this.demandMax = 0;
    }

    public Technician(double x, double y, double distanceCost, double dayCost, int demandMax) {
        super(3, x, y);
        this.distanceCost = distanceCost;
        this.dayCost = dayCost;
        this.cost = 0;
        this.demandMax = demandMax;
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
        return "modele.Technician[ id=" + id + " ]";
    }

}
