package dao;

import java.util.Collection;
import javax.persistence.Query;
import modele.Customer;
import modele.Instance;
import modele.Vehicle;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaCustomerDao extends JpaDao<Customer> implements CustomerDao {

    private static JpaCustomerDao instance;

    /**
     * Default Constructor
     */
    private JpaCustomerDao() {
        super(Customer.class);
    }

    /**
     * get the instance
     * @return 
     */
    public static JpaCustomerDao getInstance() {
        if (instance == null) {
            instance = new JpaCustomerDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Customer> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Customer obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Customer obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Customer obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * find customers not delivered
     * @return 
     */
    @Override
    public Collection<Customer> findNotServed() {
        Query query = this.getEm().createNamedQuery("Customer.findNotServed");
        return query.getResultList();
    }

    /**
     * find customer by instance
     * @param instance
     * @return 
     */
    @Override
    public Collection<Customer> findByInstance(Instance instance) {
        Query query = this.getEm().createNamedQuery("Customer.findByInstance")
                .setParameter("instance", instance);

        return query.getResultList();
    }

    /**
     * find a customer in an instance
     * @param instance
     * @param id
     * @return 
     */
    @Override
    public Customer findByInstanceCustomer(Instance instance, Integer id) {
        Query query = this.getEm().createNamedQuery("Customer.findByInstanceCustomer")
                .setParameter("instance", instance)
                .setParameter("id", id);

        return (Customer) query.getSingleResult();
    }

}
