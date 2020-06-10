package dao;

import java.util.Collection;
import modele.Customer;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface CustomerDao extends Dao<Customer> {

    /**
     * Renvoie tous les clients non livrés par un véhicule
     *
     * @return une collection de clients
     */
    public Collection<Customer> findNotServed();
}