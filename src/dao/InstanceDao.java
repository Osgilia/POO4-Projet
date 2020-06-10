package dao;

import modele.Instance;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface InstanceDao extends Dao<Instance> {

    /**
     * Renvoie l'instance dont le nom est name
     *
     * @param name
     * @return
     */
    public Instance findByName(String name);
}