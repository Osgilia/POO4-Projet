/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public Instance() {
        this.name = "DEFAULT NAME";
        this.dataset = "DEFAULT NAME";
        this.planningList = new ArrayList<>();
    }

    public Instance(String name, String dataset) {
        this();
        this.name = name;
        this.dataset = dataset;
    }

    public String getName() {
        return name;
    }

    public String getDataset() {
        return dataset;
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
}
