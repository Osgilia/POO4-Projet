package dao;

import java.util.Collection;
import modele.Customer;
import modele.Instance;

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
    
    public Collection<Customer> findByInstance(Instance instance);
    
    public Customer findByInstanceCustomer(Instance instance, Integer id);

}
