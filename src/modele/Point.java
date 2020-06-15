package modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Point implements Serializable {

    /**
     * **********************
     * ATTRIBUTES * *********************
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "IDPOINT")
    private Integer idPoint;
    
    @Column(name = "IDLOCATION")
    private Integer idLocation;

    @Column(name = "POINTTYPE")
    private Integer pointType;

    @Basic(optional = false)
    @Column(name = "X")
    private double x;

    @Basic(optional = false)
    @Column(name = "Y")
    private double y;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "depart")
    @MapKey(name = "arrivee")
    private Map<Point, Route> myRoutes;

    /**
     * Instance using this point
     */
    @JoinColumn(name = "PINSTANCE", referencedColumnName = "ID")
    @ManyToOne
    private Instance pInstance;
    
    /**
     * Itineraries linked to this point with a position
     */
    @OneToMany(mappedBy = "point", fetch = FetchType.LAZY
//            cascade = {
//                CascadeType.MERGE
//            }
    )
    private Set<ItineraryPoint> itineraries;

    /**
     * **************************
     * CONSTRUCTORS * **************************
     */
    /**
     * No-arg constructor
     */
    public Point() {
        this.pointType = 1;
        this.idPoint = -1;
        this.x = 0;
        this.y = 0;
        this.myRoutes = new HashMap<>();
        this.itineraries = new HashSet<>();
    }

    /**
     * Parameterized constructor
     *
     * @param id
     * @param idLocation
     * @param pointType
     * @param x
     * @param y
     * @param instance
     */
    public Point(Integer id, Integer idLocation, Integer pointType, double x, double y, Instance instance) {
        this();
        this.idPoint = id;
        this.idLocation = idLocation;
        this.pointType = pointType;
        this.x = x;
        this.y = y;
        this.pInstance = instance;
    }

    /**
     * ******************************
     * GETTERS & SETTERS * *****************************
     */
    public Integer getIdLocation() {
        return idLocation;
    }

    public Integer getPointType() {
        return pointType;
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

    public Instance getpInstance() {
        return pInstance;
    }

    public Integer getIdPoint() {
        return idPoint;
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
        final Point other = (Point) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.idLocation, other.idLocation)) {
            return false;
        }
        if (!Objects.equals(this.pointType, other.pointType)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Point{" + "id=" + id + ", idLocation=" + idLocation + ", x=" + x + ", y=" + y + '}';
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

    /**
     * Adds an itinerary to this point
     *
     * @param itinerary
     * @return true if success
     */
    public boolean addItineraryPoint(ItineraryPoint itinerary) {
        if (itinerary != null) {
            if (this.itineraries.add(itinerary)) {
                return true;
            }
        }
        return false;
    }
}
