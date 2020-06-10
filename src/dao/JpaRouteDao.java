package dao;

import java.util.Collection;
import modele.Route;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaRouteDao extends JpaDao<Route> implements RouteDao {
    private static JpaRouteDao instance;
    private JpaRouteDao() {
        super(Route.class);
    }

    public static JpaRouteDao getInstance() {
        if(instance == null) {
            instance = new JpaRouteDao();
        }
        return instance;
    }
    
    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Route> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Route find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Route obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Route obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Route obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }
}
