package dao;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Henri, Lucas, Louis
 */
public abstract class JpaDao<T> implements Dao<T> {

    private EntityManagerFactory emf;
    private EntityManager em;
    private static final String UNITE_PERSISTENCE = "Machines2IPU";
    private Class<T> classeEntite;

    /**
     * Constructeur par défaut qui initialise le gestionnaire d'entités
     * @param classeEntite : classe des entités avec lesquelles on travaille
     */
    public JpaDao(Class<T> classeEntite) {
        this.emf = Persistence.createEntityManagerFactory(this.UNITE_PERSISTENCE);
        this.em = this.emf.createEntityManager();
        this.classeEntite = classeEntite;
    }

    /**
     * create an object in the DB
     * @param obj
     * @return 
     */
    @Override
    public boolean create(T obj) {
        EntityTransaction et = this.em.getTransaction();
        try {
            et.begin();
            this.em.persist(obj);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * update an object
     * @param obj
     * @return 
     */
    @Override
    public boolean update(T obj) {
        EntityTransaction et = this.em.getTransaction();
        try {
            et.begin();
             em.merge(obj);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * delete an object from the DB
     * @param obj
     * @return 
     */
    @Override
    public boolean delete(T obj) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(obj);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * close the entity manager
     */
    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    /**
     * find an object in the database following his Id
     * @param id
     * @return 
     */
    @Override
    public T find(Integer id) {
        return this.em.find(this.classeEntite, id);
    }

    /**
     * find all the object
     * @return 
     */
    @Override
    public Collection<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(classeEntite);
        Root<T> results = cq.from(classeEntite);
        cq.select(results);
        return em.createQuery(cq).getResultList();
    }

    /**
     * delete all the objects of a specific class
     * @return 
     */
    @Override
    public boolean deleteAll() {
        EntityTransaction et = em.getTransaction();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaDelete<T> cd = cb.createCriteriaDelete(this.classeEntite);
            et.begin();
            int delete = em.createQuery(cd).executeUpdate();
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * get the EntituManager
     * @return 
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * get the class entity
     * @return 
     */
    public Class<T> getClasseEntite() {
        return classeEntite;
    }
}
