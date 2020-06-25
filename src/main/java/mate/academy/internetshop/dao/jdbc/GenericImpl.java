package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.util.ConnectionUtil;

public abstract class GenericImpl {

    boolean deleteByQuery(String query, long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 0 ? false : true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete by query " + query, e);
        }
    }
}
