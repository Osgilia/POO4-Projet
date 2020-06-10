package dao;

import java.util.Collection;
import modele.Point;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaPointDao extends JpaDao<Point> implements PointDao {
    private static JpaPointDao instance;
    private JpaPointDao() {
        super(Point.class);
    }

    public static JpaPointDao getInstance() {
        if(instance == null) {
            instance = new JpaPointDao();
        }
        return instance;
    }
    
    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Point> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Point obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Point obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Point obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }
}
