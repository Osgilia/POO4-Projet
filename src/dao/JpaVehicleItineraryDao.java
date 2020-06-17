package dao;

import java.util.Collection;
import modele.VehicleItinerary;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaVehicleItineraryDao extends JpaDao<VehicleItinerary> implements VehicleItineraryDao {
    private static JpaVehicleItineraryDao instance;
    
    /**
     * Default constructor
     */
    private JpaVehicleItineraryDao() {
        super(VehicleItinerary.class);
    }

    //get instance
    public static JpaVehicleItineraryDao getInstance() {
        if(instance == null) {
            instance = new JpaVehicleItineraryDao();
        }
        return instance;
    }
    
    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<VehicleItinerary> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VehicleItinerary find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(VehicleItinerary obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(VehicleItinerary obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(VehicleItinerary obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }
}
