package dao;

import java.util.Collection;
import modele.Instance;
import modele.Technician;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface TechnicianDao extends Dao<Technician> {

    public Collection<Technician> findbyInstance(Instance instance);

}
