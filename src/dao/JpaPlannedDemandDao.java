package dao;

import java.util.Collection;
import modele.PlannedDemand;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaPlannedDemandDao extends JpaDao<PlannedDemand> implements PlannedDemandDao {
    private static JpaPlannedDemandDao instance;
    
    private JpaPlannedDemandDao() {
        super(PlannedDemand.class);
    }
    
    public static JpaPlannedDemandDao getInstance() {
        if(instance == null) {
            instance = new JpaPlannedDemandDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<PlannedDemand> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlannedDemand find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(PlannedDemand obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(PlannedDemand obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean create(PlannedDemand obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    } 
}
