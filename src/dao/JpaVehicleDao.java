package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import modele.Vehicle;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaVehicleDao extends JpaDao<Vehicle> implements VehicleDao {
    private static JpaVehicleDao instance;
    
    private JpaVehicleDao() {
        super(Vehicle.class);
    }
    
    public static JpaVehicleDao getInstance() {
        if(instance == null) {
            instance = new JpaVehicleDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Vehicle> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vehicle find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Vehicle obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Vehicle obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Vehicle obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Vehicle> findAllNotUsed() {
        Query query = this.getEm().createNamedQuery("Vehicle.findAllNotUsed");
        List<Vehicle> vehicules = new ArrayList<>();
        for(Object o : query.getResultList()) {
            vehicules.add((Vehicle) o);
        }
        return vehicules;
    }
}
