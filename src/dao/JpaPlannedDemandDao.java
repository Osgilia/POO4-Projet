package dao;

import java.util.Collection;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import modele.Instance;
import modele.PlannedDemand;
import modele.Planning;
import modele.VehicleItinerary;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaPlannedDemandDao extends JpaDao<PlannedDemand> implements PlannedDemandDao {

    private static JpaPlannedDemandDao instance;

    /**
     * Dafault constructor
     */
    private JpaPlannedDemandDao() {
        super(PlannedDemand.class);
    }

    /**
     * get isntance
     * @return 
     */
    public static JpaPlannedDemandDao getInstance() {
        if (instance == null) {
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

    /**
     * find by demand state and planning
     * @param stateDemand
     * @param planning
     * @return 
     */
    @Override
    public Collection<PlannedDemand> findByStatedemandAndPlanning(int stateDemand, Planning planning) {
        try{
            Query query = this.getEm().createNamedQuery("PlannedDemand.findByStatedemandAndPlanning")
                .setParameter("stateDemand", stateDemand)
                .setParameter("planning",planning);
            return query.getResultList();
        } catch(NoResultException e) {
            return null;
        }
    }
    
}
