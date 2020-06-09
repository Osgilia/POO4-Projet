package dao;

import java.util.Collection;

/**
 * Interface générique pour implémenter un DAO
 * @author Henri, Lucas, Louis
 * @param <T>
 */
public interface Dao<T> {

    public boolean create(T obj);

    public T find(Integer id);

    public Collection<T> findAll();

    public boolean update(T obj);

    public boolean delete(T obj);

    public boolean deleteAll();

    public void close();
}
