package kz.zhelezyaka.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> find(Integer id) throws SQLException;

    void save(T model);

    void update(T model);

    void delete(Integer id);

    List<T> findAll() throws SQLException;
}
