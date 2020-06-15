package dao;

import java.util.Collection;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import modele.Instance;
import modele.Planning;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaPlanningDao extends JpaDao<Planning> implements PlanningDao {

    private static JpaPlanningDao instance;
    
    private JpaPlanningDao() {
        super(Planning.class);
    }
    
    public static JpaPlanningDao getInstance() {
        if(instance == null) {
            instance = new JpaPlanningDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Planning> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Planning find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Planning obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Planning obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Planning obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Planning findByAlgoNameAndInstance(String algoName,Instance ninstance) {
        try{
            Query query = this.getEm().createNamedQuery("Planning.findByAlgoNameAndInstance")
                .setParameter("algoName", algoName)
                .setParameter("ninstance",ninstance);
            return (Planning) query.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

}
