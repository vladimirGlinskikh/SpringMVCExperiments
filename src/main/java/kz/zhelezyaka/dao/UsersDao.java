package kz.zhelezyaka.dao;

import kz.zhelezyaka.model.User;

import java.util.List;

public interface UsersDao extends CrudDao<User> {
    List<User> findAllByFirstName(String firstName);
}
