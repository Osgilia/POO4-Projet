package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    /************************
     *      ATTRIBUTES      *
     ***********************/
    
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

    /**
     * Instance using this point
     */
    @JoinColumn(name = "PINSTANCE", referencedColumnName = "ID")
    @ManyToOne
    private Instance pInstance;
    
    /**
     * ItineraryPoint(s) affected to this Itinerary
     */
    @OneToMany(mappedBy = "point",
            cascade = {
                CascadeType.PERSIST
            })
    private List<ItineraryPoint> itineraryPointList;
    
    /****************************
    *       CONSTRUCTORS        *
    ****************************/

    /**
     * No-arg constructor
     */
    public Point() {
        this.pointType = 1;
        this.x = 0;
        this.y = 0;
        this.myRoutes = new HashMap<>();
        this.itineraryPointList = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param id
     * @param pointType
     * @param x
     * @param y
     */
    public Point(Integer id, Integer pointType, double x, double y) {
        this();
        this.id = id;
        this.pointType = pointType;
        this.x = x;
        this.y = y;
        this.itineraryPointList = new ArrayList<>();
    }

    
    /********************************
     *      GETTERS & SETTERS       *
     *******************************/

    public Integer getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Instance getpInstance() {
        return pInstance;
    }

    public void setpInstance(Instance pInstance) {
        this.pInstance = pInstance;
    }

    
    /************************
     *       METHODS        *
     ***********************/

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

    /**
     * Returns the euclidian distance between two points with its integer
     * portion by excess
     *
     * @param pointB
     * @return the distance
     */
    public int computeDistance(Point pointB) {
        double distance = Math.sqrt(Math.pow(this.getX() - pointB.getX(), 2) + Math.pow(this.getY() - pointB.getY(), 2));
        return (int) Math.ceil(distance);
    }
    
  
    public boolean addItineraryPoint(Itinerary itinerary) {
        if (itinerary != null) {
            ItineraryPoint itineraryPoint = new ItineraryPoint(itinerary, this);
            if (!this.itineraryPointList.contains(itineraryPoint)) {
                this.itineraryPointList.add(itineraryPoint);
                if (itinerary.addPoint(itineraryPoint)) {
                    return true;
                }
            }
        }
        return false;
    }
}
