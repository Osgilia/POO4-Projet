package dao;

import java.util.Collection;
import javax.persistence.Query;
import modele.Customer;
import modele.Demand;
import modele.Instance;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaDemandDao extends JpaDao<Demand> implements DemandDao {
    private static JpaDemandDao instance;
    
    private JpaDemandDao() {
        super(Demand.class);
    }
    
    public static JpaDemandDao getInstance() {
        if(instance == null) {
            instance = new JpaDemandDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Demand> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public Demand find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Demand obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Demand obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean create(Demand obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    } 

    @Override
    public Collection<Demand> findByInstance(Instance instance) {
        Query query = this.getEm().createNamedQuery("Demand.findByInstance")
                .setParameter("instance", instance);

        return query.getResultList();
    }
}
