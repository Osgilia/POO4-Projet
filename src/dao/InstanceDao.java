package dao;

import java.util.Collection;
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

    /**
     * 
     * @param id
     * @return 
     */
    public Instance findById(Integer id);
    
    /**
     * 
     * @param id
     * @return 
     */
    public Collection<Instance> findByDataset(String dataset);

}
