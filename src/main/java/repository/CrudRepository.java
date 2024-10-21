package repository;

import entity.CustomerEntity;
import javafx.collections.ObservableList;

import java.util.List;

public interface CrudRepository <T,ID> extends SuperDao{
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(ID id);
    List<T> findAll();
     T search(String id);
    ObservableList<String> getAllId();
}
