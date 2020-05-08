package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;
import mate.academy.internetshop.web.filters.AuthorizationFilter;
import org.apache.log4j.Logger;

public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);

    @Override
    public Product create(Product element) {
        String query = "INSERT INTO ";
        try(Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
        } catch (SQLException e) {
            LOGGER.error("Can't create statement", e);
            throw new RuntimeException("Can't create statement", e);
        }

        return null;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Product update(Product element) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
