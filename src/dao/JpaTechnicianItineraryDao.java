package dao;

import java.util.Collection;
import modele.TechnicianItinerary;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaTechnicianItineraryDao extends JpaDao<TechnicianItinerary> implements TechnicianItineraryDao {
    private static JpaTechnicianItineraryDao instance;
    private JpaTechnicianItineraryDao() {
        super(TechnicianItinerary.class);
    }

    public static JpaTechnicianItineraryDao getInstance() {
        if(instance == null) {
            instance = new JpaTechnicianItineraryDao();
        }
        return instance;
    }
    
    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TechnicianItinerary> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TechnicianItinerary find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(TechnicianItinerary obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(TechnicianItinerary obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(TechnicianItinerary obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }
}
