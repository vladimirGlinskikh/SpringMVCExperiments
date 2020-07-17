package kz.zhelezyaka.dao;

import kz.zhelezyaka.model.Car;
import kz.zhelezyaka.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersDaoJdbcTemplateImpl implements UsersDao {
    private JdbcTemplate template;
    private Map<Integer, User> usersMap = new HashMap<>();

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM fix_user";

    //language=SQL
    private final String SQL_USER_WITH_CAR =
            "SELECT fix_user.*, fix_car.id AS car_id, fix_car.model  FROM fix_user LEFT JOIN fix_car ON fix_user.id = fix_car.owner_id WHERE fix_user.id = 1";


    //language=SQL
    private final String SQL_SELECT_ALL_BY_FIRST_NAME =
            "SELECT * FROM fix_user WHERE first_name = 'Vladimir'";

    public UsersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (ResultSet resultSet, int i) -> {
        Integer id = resultSet.getInt("id");
        if (!usersMap.containsKey(id)) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");

            User user = new User(id, firstName, lastName, new ArrayList<>());
            usersMap.put(id, user);
        }

        Car car = new Car(resultSet.getInt("car_id"),
                resultSet.getString("model"),
                usersMap.get(id));

        usersMap.get(id).getCars().add(car);
        return usersMap.get(id);
    };

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return template.query(SQL_SELECT_ALL_BY_FIRST_NAME, userRowMapper, firstName);
    }

    @Override
    public Optional<User> find(Integer id) throws SQLException {
        template.query(SQL_SELECT_ALL, userRowMapper, id);
        if (usersMap.containsKey(id)) {
            return Optional.of(usersMap.get(id));
        }
        return Optional.empty();
    }

    @Override
    public void save(User model) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> result = template.query(SQL_USER_WITH_CAR, userRowMapper);
        usersMap.clear();
        return result;
    }
}
