package dao;

import java.util.Collection;
import modele.MachineType;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaMachineTypeDao extends JpaDao<MachineType> implements MachineTypeDao {
    private static JpaMachineTypeDao instance;
    
    private JpaMachineTypeDao() {
        super(MachineType.class);
    }
    
    public static JpaMachineTypeDao getInstance() {
        if(instance == null) {
            instance = new JpaMachineTypeDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<MachineType> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MachineType find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(MachineType obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(MachineType obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean create(MachineType obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    } 
}
