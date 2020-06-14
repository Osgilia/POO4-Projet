package modele;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Relation entity between an itinerary and a point
 *
 * @author Lucas, Henri, Louis
 */
@Entity
public class ItineraryPoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "ITINERARY_ID", referencedColumnName = "ID")
    @ManyToOne
    private Itinerary itinerary;

    @JoinColumn(name = "POINT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Point point;

    @Column(name = "POSITION")
    private int position;

    /**
     * No-arg constructor
     */
    public ItineraryPoint() {
        this.position = -1;
    }

    /**
     * Parameterized constructor
     *
     * @param itinerary
     * @param point
     * @param position
     */
    public ItineraryPoint(Itinerary itinerary, Point point, int position) {
        this();
        this.itinerary = itinerary;
        this.point = point;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.itinerary);
        hash = 17 * hash + Objects.hashCode(this.point);
        hash = 17 * hash + this.position;
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
        final ItineraryPoint other = (ItineraryPoint) obj;
        if (this.position != other.position) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.itinerary, other.itinerary)) {
            return false;
        }
        if (!Objects.equals(this.point, other.point)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Position " + position;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public Point getPoint() {
        return point;
    }
}
