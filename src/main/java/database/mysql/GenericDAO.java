package database.mysql;



import java.util.List;

public interface GenericDAO<T> {
    public List<T> getAll();
    public T getById(int id);
    public T getByName(String name);
    public void storeOne(T type);

}
