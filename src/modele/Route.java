package modele;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Route thats connects two points
 *
 * @author Henri, Louis, Lucas
 */
@Entity
@Table(name = "ROUTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r")
    , @NamedQuery(name = "Route.findById", query = "SELECT r FROM Route r WHERE r.id = :id")
    , @NamedQuery(name = "Route.findByDistance", query = "SELECT r FROM Route r WHERE r.distance = :distance")})

public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DISTANCE")
    private Double distance;

    @JoinColumn(name = "NARRIVEE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Point arrivee;

    @JoinColumn(name = "NDEPART", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Point depart;

    /**
     * No-arg constructor
     */
    public Route() {
        this.distance = 0.0;
    }
    
    /**
     * Parameterized constructor
     * @param depart
     * @param arrivee
     * @param distance 
     */
    public Route(Point depart, Point arrivee, Double distance) {
        this();
        this.depart = depart;
        this.arrivee = arrivee;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Route other = (Route) obj;
        if (!Objects.equals(this.distance, other.distance)) {
            return false;
        }
        if (!Objects.equals(this.arrivee, other.arrivee)) {
            return false;
        }
        if (!Objects.equals(this.depart, other.depart)) {
            return false;
        }
        return true;
    }

    public Double getDistance() {
        return distance;
    }

    public Point getArrivee() {
        return arrivee;
    }

    public Point getDepart() {
        return depart;
    }

    @Override
    public String toString() {
        return "Route " + id + ": From " + this.depart + " to " + this.arrivee + " = " + this.distance;
    }
}
