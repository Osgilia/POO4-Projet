package dao;

import javax.persistence.Query;
import modele.Instance;
import modele.Planning;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface PlanningDao extends Dao<Planning> {
    
    /**
     * Return the planning with this solution
     *
     * @param name
     * @return
     */
    public Planning findByAlgoNameAndInstance(String algoName, Instance ninstance);
}
