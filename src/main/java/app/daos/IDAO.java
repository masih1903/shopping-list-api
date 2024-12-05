package app.daos;

import java.util.List;

public interface IDAO<T, D> {

    T getById(Integer id);

    List<T> getAll();

    T create(D dto);

    T update(Integer id, D dto);

    void delete(Integer id);
}
