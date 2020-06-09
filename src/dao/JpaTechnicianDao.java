package dao;

import java.util.Collection;
import modele.Technician;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaTechnicianDao extends JpaDao<Technician> implements TechnicianDao {
    private static JpaTechnicianDao instance;
    private JpaTechnicianDao() {
        super(Technician.class);
    }

    public static JpaTechnicianDao getInstance() {
        if(instance == null) {
            instance = new JpaTechnicianDao();
        }
        return instance;
    }
    
    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Technician> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Technician find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Technician obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Technician obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Technician obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }
}
