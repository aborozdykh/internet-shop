package mate.academy.internetshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.util.ConnectionUtil;

public interface GenericDao<T, K> {
    T create(T element);

    Optional<T> get(K id);

    List<T> getAll();

    T update(T element);

    boolean delete(K id);

    default boolean deleteByQuery(String query, long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
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
