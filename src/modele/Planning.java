/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
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
 * Solution de planning associée à une instance
 *
 * @author Lucas, Louis, Henri
 */
@Entity
@Table(name = "PLANNING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planning.findAll", query = "SELECT p FROM Planning p")
    , @NamedQuery(name = "Planning.findById", query = "SELECT p FROM Planning p WHERE p.id = :id")
    , @NamedQuery(name = "Planning.findByCost", query = "SELECT p FROM Planning p WHERE p.cost = :cost")})
public class Planning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "COST")
    private double cost;

    @JoinColumn(name = "NINSTANCE", referencedColumnName = "ID")
    @ManyToOne
    private Instance ninstance;

    /**
     * Nombre de jours dans l'horizon de planification
     */
    @Column(name = "NBDAYS")
    private int nbDays;
    
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "nplanning")
    private Set<DayHorizon> days;

    /**
     * No-argument constructor
     */
    public Planning() {
        this.cost = 0;
        this.nbDays = 0;
        this.days = new HashSet<>();
    }

    /**
     * Parameterized constructor
     * @param ninstance
     * @param nbDays 
     */
    public Planning(Instance ninstance, int nbDays) {
        this();
        this.ninstance = ninstance;
        this.nbDays = nbDays;
    }

    public void setNinstance(Instance ninstance) {
        this.ninstance = ninstance;
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
        final Planning other = (Planning) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.ninstance, other.ninstance)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Planning{" + "cost=" + cost + ", nbDays=" + nbDays + '}';
    }
}
