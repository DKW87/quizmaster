package database.mysql;

import java.util.List;

public interface GenericDAO<T> {
    public List<T> getAll();
    public T getOneById(int id);
    public void storeOne(T type);
}
