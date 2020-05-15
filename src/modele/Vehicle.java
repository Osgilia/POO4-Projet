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
 *
 * @author Osgilia
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
       @Column(name = "COUT")
    private Double cout;
    @Column(name = "CAPACITEUTILISEE")
    private Integer capaciteutilisee;
    @Column(name = "CAPACITE")
    private Integer capacite;
    @OneToMany(mappedBy = "nvehicule")
    private List<Customer> listCustomer;
   /* @JoinColumn(name = "NINSTANCE", referencedColumnName = "ID")
    @ManyToOne
    private Instance instance;
    @JoinColumn(name = "NPLANNING", referencedColumnName = "ID")
    @ManyToOne
    private Planning planning;
    @JoinColumn(name = "NDEPOT", referencedColumnName = "ID")*/
    @ManyToOne
    private Point depot;

    public Vehicle() {
        this.capacite = 0;
        this.capaciteutilisee = 0;
        this.cout = 0.0;
        /*this.instance = new Instance();
        this.planning = new Planning();*/
        this.depot = new Point();
        this.listCustomer = new ArrayList<Customer>();
    }

    public Vehicle(Depot depot, Integer capacite) {
        this();
        this.capacite = capacite;
        this.depot = depot;

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
        return "modele.Vehicle[ id=" + id + " ]";
    }
    
}
