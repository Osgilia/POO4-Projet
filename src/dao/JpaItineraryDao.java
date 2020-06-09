package dao;

import java.util.Collection;
import modele.Itinerary;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaItineraryDao extends JpaDao<Itinerary> implements ItineraryDao {
    private static JpaItineraryDao instance;
    
    private JpaItineraryDao() {
        super(Itinerary.class);
    }
    
    public static JpaItineraryDao getInstance() {
        if(instance == null) {
            instance = new JpaItineraryDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Itinerary> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Itinerary find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Itinerary obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Itinerary obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean create(Itinerary obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    } 
}
