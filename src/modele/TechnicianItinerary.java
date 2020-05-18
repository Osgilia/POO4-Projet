package modele;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
 * Represents an ordered sequence in which customers have their machines
 * installed
 *
 * @author Lucas, Louis, Henri
 */
@Entity
@Table(name = "TECHNICIANITINERARY")
@XmlRootElement
@NamedQueries({})
@DiscriminatorValue("2")
public class TechnicianItinerary extends Itinerary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "TECHNICIAN_ID")
    private Technician technician;

    /**
     * No-argument constructor
     */
    public TechnicianItinerary() {
        super();
    }

    /**
     * Parameterized constructor
     * @param technician
     */
    public TechnicianItinerary(Technician technician) {
        super(2);
        this.technician = technician;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TechnicianItinerary)) {
            return false;
        }
        TechnicianItinerary other = (TechnicianItinerary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "- Itinerary of " + technician + " :" + super.toString();
    }

    public Technician getTechnician() {
        return technician;
    }
    
}
