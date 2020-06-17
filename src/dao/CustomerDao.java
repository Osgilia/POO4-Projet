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
     * find all the customers not delivered
     * @return une collection de clients
     */
    public Collection<Customer> findNotServed();
    
    /**
     * find customers by Instance
     * @param instance
     * @return 
     */
    public Collection<Customer> findByInstance(Instance instance);
    
    /**
     * find a customer in an isntance
     * @param instance
     * @param id
     * @return 
     */
    public Customer findByInstanceCustomer(Instance instance, Integer id);

}
