package dao;

import java.util.Collection;
import modele.Instance;

/**
 *
 * @author Henri, Lucas, Louis
 */
public interface InstanceDao extends Dao<Instance> {

    /**
     * find the instance by name
     *
     * @param name
     * @return
     */
    public Instance findByName(String name);

    /**
     * find the instance by id
     * @param id
     * @return 
     */
    public Instance findById(Integer id);
    
    /**
     * find instance by dataset
     * @param dataset
     * @return 
     */
    public Collection<Instance> findByDataset(String dataset);
    
        /**
     * find instance by dataset and name
     * @param name
     * @param dataset
     * @return 
     */
    public Instance findByNameAndDataSet(String name, String dataset);

}
