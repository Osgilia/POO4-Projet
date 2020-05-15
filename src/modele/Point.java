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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Point implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "POINTTYPE")
    private Integer pointType;
    @Basic(optional = false)
    @Column(name = "X")
    private double x;
    @Basic(optional = false)
    @Column(name = "Y")
    private double y;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depart")
    private Map<Point, Route> myRoutes;

    public Point() {
        this.pointType = 1;
        this.x = 0;
        this.y = 0;

    }

    public Point(Integer pointType, double x, double y) {
        this();
        this.pointType = pointType;
        this.x = x;
        this.y = y;
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
        if (!(object instanceof Point)) {
            return false;
        }
        Point other = (Point) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean addDestination(Point p, Double distance) {
        if (p != null) {
            Route r = new Route(this, p, distance);
            this.myRoutes.put(p, r);
            if (this.myRoutes.containsKey(r)) {
                return true;
            }
        }
        return false;
    }

    public double getDistanceTo(Point key) {
        HashMap<Point, Route> routes = new HashMap<Point, Route>(this.myRoutes);
        Iterator hashMapIterator = routes.entrySet().iterator();
        Point pointArrivee = null;
        Route route = null;
        while (hashMapIterator.hasNext()) {
            Map.Entry me = (Map.Entry) hashMapIterator.next();
            route = (Route) me.getValue();
            pointArrivee = (Point) me.getKey();
            if (pointArrivee == key) {
                break;
            } else {
                pointArrivee = null;
                route = null;
            }
        }
        if (pointArrivee == null || route == null) {
            return Double.POSITIVE_INFINITY;
        }
        return route.getDistance();
    }
    
    
    @Override
    public String toString() {
        return "modele.Point[ id=" + id + " ]";
    }

}
