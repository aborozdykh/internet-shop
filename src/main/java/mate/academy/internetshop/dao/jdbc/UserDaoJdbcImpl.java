package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
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

@Dao
public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var user = getUserFromResultSet(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by login", e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (user_name, login, password) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getLong(1));
            }
            setUserRoles(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var user = getUserFromResultSet(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user", e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * from users";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
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
        String query = "UPDATE users "
                + "SET user_name = ?, login = ?, password = ? "
                + "WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getUserId());
            deleteUserRoles(user);
            setUserRoles(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user", e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        String deleteUserFromUsersQuery = "DELETE FROM users WHERE user_id = ?";
        String deleteUserFromUsersRolesQuery = "DELETE FROM users_roles WHERE user_id = ?";
        deleteByQuery(deleteUserFromUsersRolesQuery, id);
        return deleteByQuery(deleteUserFromUsersQuery, id);
    }

    private Set<Role> getUserRoles(User user) {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT r.role_name "
                + "FROM users_roles ur "
                + "INNER JOIN roles r "
                + "ON  ur.role_id=r.role_id "
                + "WHERE ur.user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("role_name")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user's roles", e);
        }
        return roles;
    }

    private User getUserFromResultSet(ResultSet resultSet) {
        try {
            Long userId = resultSet.getLong("user_id");
            String name = resultSet.getString("user_name");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            var user = new User(userId, name, login, password);
            user.setRoles(getUserRoles(user));
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user from ResultSet", e);
        }
    }

    private void setUserRoles(User user) {
        String selectRoleIdQuery = "SELECT role_id FROM roles WHERE role_name = ?";
        String insertUsersRolesQuery = "INSERT INTO users_roles (user_id, role_id) "
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Role role : user.getRoles()) {
                PreparedStatement selectStatement =
                        connection.prepareStatement(selectRoleIdQuery);
                selectStatement.setString(1, role.getRoleName().name());
                ResultSet resultSet = selectStatement.executeQuery();
                PreparedStatement insertStatement =
                        connection.prepareStatement(insertUsersRolesQuery);
                insertStatement.setLong(1, user.getUserId());
                insertStatement.setLong(2, resultSet.getLong("role_id"));
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set user's roles", e);
        }
    }

    private void deleteUserRoles(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM users_roles WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user's roles", e);
        }
    }
}
