package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;


public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users u JOIN users_roles ur ON u.user_id = ur.user_id JOIN "
                + "roles r ON ur.role_id = r.role_id WHERE u.login = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery(query);
            return Optional.of(getUserFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by login", e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name , login, password) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            setUserRole(user, Role.RoleName.USER);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users u JOIN users_roles ur ON u.user_id = ur.user_id JOIN "
                + "roles r ON ur.role_id = r.role_id WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery(query);
            return Optional.of(getUserFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user", e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(getUserFromResultSet(resultSet));
            }
            return userList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users", e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET user_name = ?, login = ? WHERE password = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            if (statement.executeUpdate() == 0) {
                LOGGER.warn("Updating user failed, no rows affected");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user", e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        String deleteUserFromUsersQuery = "DELETE FROM users WHERE user_id = ?";
        String deleteUserFromShoppingCartsQuery = "DELETE FROM shopping_carts WHERE user_id = ?";
        String deleteUserFromUsersRolesQuery = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            deleteByQuery(connection, deleteUserFromShoppingCartsQuery, id);
            deleteByQuery(connection, deleteUserFromUsersRolesQuery, id);
            return deleteByQuery(connection, deleteUserFromUsersQuery, id);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete product", e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) {
        try {
            var user = new User();
            resultSet.next();
            user.setUserId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            resultSet.beforeFirst();
            while (resultSet.next()) {
                String role = resultSet.getString("role_name");
                user.setRoles(Set.of(Role.of(role)));
            }
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user", e);
        }
    }

    private void setUserRole(User user, Role.RoleName roleName) {
        String query = "INSERT INTO users_roles VALUE "
                + "((SELECT id FROM roles WHERE roles.name = ?), ?)";
        executeQueryForUserRole(user, roleName, query);
    }

    private void deleteUserRole(User user, Role.RoleName roleName) {
        String query = "DELETE FROM users_roles WHERE role_id =  "
                + "(SELECT id FROM roles WHERE roles.name = ?) AND user_id = ?)";
        executeQueryForUserRole(user, roleName, query);
    }

    private void executeQueryForUserRole(User user, Role.RoleName roleName, String query) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roleName.toString());
            statement.setLong(2, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set user's role", e);
        }
    }

    private boolean deleteByQuery(Connection connection, String query, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete by query " + query, e);
        }
    }
}
