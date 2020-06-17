package dao;

import java.util.Collection;
import modele.DayHorizon;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaDayHorizonDao extends JpaDao<DayHorizon> implements DayHorizonDao {
    private static JpaDayHorizonDao instance;
    
    /**
     * Default Constructor
     */
    private JpaDayHorizonDao() {
        super(DayHorizon.class);
    }
    
    /**
     * get instance
     * @return 
     */
    public static JpaDayHorizonDao getInstance() {
        if(instance == null) {
            instance = new JpaDayHorizonDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<DayHorizon> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DayHorizon find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(DayHorizon obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(DayHorizon obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean create(DayHorizon obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    } 
}
