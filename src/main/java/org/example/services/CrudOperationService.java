package org.example.services;

import java.util.List;

public interface CrudOperationService<T, E> {

    List<T> findAll();
    T findOne(E id);
    Boolean save(T modelDTO);
    void update(E id, T modelDTO);
    void delete(E id);
}
