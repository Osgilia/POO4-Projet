package dao;

import java.util.Collection;
import modele.Demand;
import modele.Instance;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface DemandDao extends Dao<Demand> {

    public Collection<Demand> findByInstance(Instance instance);

}
