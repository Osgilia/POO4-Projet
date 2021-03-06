package dao;

import java.util.Collection;
import javax.persistence.Query;
import modele.Instance;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class JpaInstanceDao extends JpaDao<Instance> implements InstanceDao {

    private static JpaInstanceDao instance;

    /**
     * Default Constructor
     */
    private JpaInstanceDao() {
        super(Instance.class);
    }

    /**
     * get instance
     *
     * @return
     */
    public static JpaInstanceDao getInstance() {
        if (instance == null) {
            instance = new JpaInstanceDao();
        }
        return instance;
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Instance> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Instance find(Integer id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Instance obj) {
        return super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Instance obj) {
        return super.update(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Instance obj) {
        return super.create(obj); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * find instance by name
     *
     * @param name
     * @return
     */
    @Override
    public Instance findByName(String name) {
        Query query = this.getEm().createNamedQuery("Instance.findByName")
                .setParameter("name", name);

        return (Instance) query.getSingleResult();
    }

    /**
     * find instance by id
     *
     * @param id
     * @return
     */
    @Override
    public Instance findById(Integer id) {
        Query query = this.getEm().createNamedQuery("Instance.findById")
                .setParameter("id", id);

        return (Instance) query.getSingleResult();
    }

    /**
     * find instance by dataset
     *
     * @param dataset
     * @return
     */
    @Override
    public Collection<Instance> findByDataset(String dataset) {
        Query query = this.getEm().createNamedQuery("Instance.findByDataset")
                .setParameter("dataset", dataset);

        return query.getResultList();
    }

    /**
     * find instance by name
     *
     * @param name
     * @return
     */
    @Override
    public Instance findByNameAndDataSet(String name, String dataset) {
        Query query = this.getEm().createNamedQuery("Instance.findByNameAndDataSet")
                .setParameter("name", name)
                .
                setParameter("dataset", dataset);

        return (Instance) query.getSingleResult();
    }

}
