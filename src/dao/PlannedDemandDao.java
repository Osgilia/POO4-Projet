package dao;

import java.util.Collection;
import modele.Instance;
import modele.PlannedDemand;
import modele.Planning;
import modele.VehicleItinerary;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface PlannedDemandDao extends Dao<PlannedDemand> {

    /**
     * Return the planning with this solution
     *
     * @param name
     * @return
     */
    public Collection<PlannedDemand> findByStatedemandAndPlanning(int stateDemand, Planning planning);
    
}
