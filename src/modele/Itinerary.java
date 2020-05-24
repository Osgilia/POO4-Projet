package modele;

import java.io.Serializable;
import java.util.Objects;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a sequence in which customers are visited
 *
 * @author Henri, Lucas, Louis
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ITINERARYTYPE",
        discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "ITINERARY")
@XmlRootElement
public class Itinerary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Vehicle or technician itinerary
     */
    @Column(name = "ITINERARYTYPE")
    private Integer itineraryType;

    /**
     * Day of the planning horizon in which the itinerary takes place
     */
    @ManyToOne
    @JoinColumn(name = "DAYHORIZON_ID")
    private DayHorizon dayHorizon;

    /**
     * No-argument constructor
     */
    public Itinerary() {
        this.itineraryType = 1;
    }

    /**
     * Parameterized constructor
     *
     * @param itineraryType : 1 or 2
     */
    public Itinerary(Integer itineraryType) {
        this();
        this.itineraryType = itineraryType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.itineraryType);
        hash = 41 * hash + Objects.hashCode(this.dayHorizon);
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
        final Itinerary other = (Itinerary) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.itineraryType, other.itineraryType)) {
            return false;
        }
        if (!Objects.equals(this.dayHorizon, other.dayHorizon)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Itinerary Type = " + itineraryType;
    }

    public void setDayHorizon(DayHorizon dayHorizon) {
        if (dayHorizon != null) {
            this.dayHorizon = dayHorizon;
        }
    }

    public DayHorizon getDayHorizon() {
        return dayHorizon;
    }

    public int getDayNumber() {
        return dayHorizon.getDayNumber();
    }
    
    public void updateCostDay() {
        this.dayHorizon.updateCost();
    }

    /**
     * Toggles the state of a demand
     *
     * @param d : demand to toggle
     */
    protected void toggleDemand(Demand d) {
        this.dayHorizon.toggleDemand(d);
    }
}
