package dao;

import java.util.Collection;
import javax.persistence.Query;
import modele.Instance;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaInstanceDao extends JpaDao<Instance> implements InstanceDao {
    private static JpaInstanceDao instance;
    
    public JpaInstanceDao() {
        super(Instance.class);
    }
    
    public static JpaInstanceDao getInstance() {
        if(instance == null) {
            instance = new JpaInstanceDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Instance> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Instance find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Instance obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Instance obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Instance obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Instance findByName(String name) {
        Query query = this.getEm().createNamedQuery("Instance.findByNom")
                .setParameter("nom", name);
        
        return (Instance) query.getSingleResult();
    }

    @Override
    public Instance findById(Integer id) {
        Query query = this.getEm().createNamedQuery("Instance.findById")
                .setParameter("id", id);
        
        return (Instance) query.getSingleResult();    }
    
}
