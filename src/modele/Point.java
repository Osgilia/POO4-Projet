/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class representing a point
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "POINTTYPE",
        discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "POINT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Point.findAll", query = "SELECT p FROM Point p")
    , @NamedQuery(name = "Point.findById", query = "SELECT p FROM Point p WHERE p.id = :id")
    , @NamedQuery(name = "Point.findByX", query = "SELECT p FROM Point p WHERE p.x = :x")
    , @NamedQuery(name = "Point.findByY", query = "SELECT p FROM Point p WHERE p.y = :y")})
public abstract class Point implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "POINTTYPE")
    private Integer pointType;

    @Basic(optional = false)
    @Column(name = "X")
    private double x;

    @Basic(optional = false)
    @Column(name = "Y")
    private double y;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depart")
    @MapKey(name = "arrivee")
    private Map<Point, Route> myRoutes;

    @OneToMany(mappedBy = "ndepot")
    private Set<Vehicle> collectionVehicules;

    public Point() {
        this.pointType = 1;
        this.x = 0;
        this.y = 0;
        this.myRoutes = new HashMap<>();
        this.collectionVehicules = new HashSet<>();
    }

    public Point(Integer id, Integer pointType, double x, double y) {
        this();
        this.id = id;
        this.pointType = pointType;
        this.x = x;
        this.y = y;
    }

    public Integer getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Point)) {
            return false;
        }
        Point other = (Point) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[x = " + this.x + ", y = " + this.y + "]";
    }

    /**
     * Adds a route between this and p
     *
     * @param p : point of arrival
     * @param distance
     * @return true if success
     */
    public boolean addDestination(Point p, double distance) {
        if (p != null) {
            Route r = new Route(this, p, distance);
            this.myRoutes.put(p, r);
            if (this.myRoutes.containsKey(r)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the distance between this and key
     *
     * @param key : point
     * @return distance or infinity if error
     */
    public double getDistanceTo(Point key) {
        if (this.myRoutes.containsKey(key)) {
            return this.myRoutes.get(key).getDistance();
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

//    public double getDistanceTo(Point key) {
//        HashMap<Point, Route> routes = new HashMap<Point, Route>(this.myRoutes);
//        Iterator hashMapIterator = routes.entrySet().iterator();
//        Point pointArrivee = null;
//        Route route = null;
//        while (hashMapIterator.hasNext()) {
//            Map.Entry me = (Map.Entry) hashMapIterator.next();
//            route = (Route) me.getValue();
//            pointArrivee = (Point) me.getKey();
//            if (pointArrivee == key) {
//                break;
//            } else {
//                pointArrivee = null;
//                route = null;
//            }
//        }
//        if (pointArrivee == null || route == null) {
//            return Double.POSITIVE_INFINITY;
//        }
//        return route.getDistance();
//    }
}
