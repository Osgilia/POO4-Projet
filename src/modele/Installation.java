/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity that represents the relationship between a machine and a technician
 *
 * @author Lucas, Henri, Louis
 */
@Entity
public class Installation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "INSTALLATION_MACHINE_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    })
    private Machine machine;

    @JoinColumn(name = "INSTALLATION_TECHNICIAN_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = {
        CascadeType.PERSIST
    })
    private Technician technician;

    /**
     * No-argument constructor
     */
    public Installation() {
        this.machine = null;
        this.technician = null;
    }

    /**
     * Parameterized constructor
     *
     * @param machine
     * @param technician
     */
    public Installation(Machine machine, Technician technician) {
        this.machine = machine;
        this.technician = technician;
    }

    public Machine getMachine() {
        return machine;
    }

    public Technician getTechnician() {
        return technician;
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
        final Installation other = (Installation) obj;
        if (!Objects.equals(this.machine, other.machine)) {
            return false;
        }
        if (!Objects.equals(this.technician, other.technician)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TechnicianMachine[ id=" + id + " ]";
    }

}
