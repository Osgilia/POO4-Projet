package dao;

import java.util.Collection;
import modele.Depot;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaDepotDao extends JpaDao<Depot> implements DepotDao {
    private static JpaDepotDao instance;
    
    private JpaDepotDao() {
        super(Depot.class);
    }
    
    /**
     * get instance
     * @return 
     */
    public static JpaDepotDao getInstance() {
        if(instance == null) {
            instance = new JpaDepotDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Depot> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Depot find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Depot obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Depot obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean create(Depot obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    } 
}
