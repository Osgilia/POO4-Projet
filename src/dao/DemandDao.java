package dao;

import java.util.Collection;
import modele.Demand;
import modele.Instance;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface DemandDao extends Dao<Demand> {

    /**
     * find demands by instance
     * @param instance
     * @return 
     */
    public Collection<Demand> findByInstance(Instance instance);

}
