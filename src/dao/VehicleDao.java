package dao;

import java.util.Collection;
import modele.Vehicle;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface VehicleDao extends Dao<Vehicle> {

    /**
     * Permet de renvoyer tous les véhicules non encore utilisés Càd ceux non
     * affectés à un planning
     *
     * @return
     */
    public Collection<Vehicle> findAllNotUsed();
}
