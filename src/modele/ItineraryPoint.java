/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author sylvy
 */
@Entity
public class ItineraryPoint implements Serializable {

    /************************
     *      ATTRIBUTES      *
     ***********************/

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "ITINERARY_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    })
    private Itinerary itinerary;

    @JoinColumn(name = "POINT_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    })
    private Point point;
    
    
    /****************************
    *       CONSTRUCTORS        *
    ****************************/
    
    /**
     * No-argument constructor
     */
    public ItineraryPoint() {
        this.itinerary = null;
        this.point = null;
    }

    /**
     * Parameterized constructor
     * @param itinerary
     * @param point 
     */
    public ItineraryPoint(Itinerary itinerary, Point point) {
        this.itinerary = itinerary;
        this.point = point;
    }

    
    /********************************
     *      GETTERS & SETTERS       *
     *******************************/
    
    public Itinerary getItinerary() {
        return itinerary;
    }

    public Point getPoint() {
        return point;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItineraryPoint)) {
            return false;
        }
        ItineraryPoint other = (ItineraryPoint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItineraryPoint{" + "id=" + id + ", itinerary=" + itinerary + ", point=" + point + '}';
    }
    
    
}
