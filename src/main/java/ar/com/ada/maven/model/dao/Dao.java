package ar.com.ada.maven.model.dao;

import java.util.Collection;

public interface Dao<T> {

Collection<T> findAll();

T findById(Integer id);

Boolean save(T t);

Boolean update(T t, Integer id);

Boolean delete(Integer id);


}
