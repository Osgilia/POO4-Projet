package dao;

import java.util.Collection;
import modele.Instance;
import modele.Vehicle;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface VehicleDao extends Dao<Vehicle> {

    /**
     * return all vehicles not yet used, i.e. those not assigned to a schedule
     *
     * @return
     */
    public Collection<Vehicle> findAllNotUsed();

    public Vehicle findbyInstance(Integer instanceId);

}
