package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;
import mate.academy.internetshop.web.filters.AuthorizationFilter;
import org.apache.log4j.Logger;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (product_name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            // I don't know exactly if I need to do this check because next 'try' block do the same
            if (statement.executeUpdate() == 0) {
                LOGGER.error("Creating product failed, no rows affected");
                throw new DataProcessingException("Creating product failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getLong(1));
                } else {
                    LOGGER.error("Creating product failed, no ID obtained");
                    throw new DataProcessingException("Creating product failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Can't create statement", e);
            throw new DataProcessingException("Can't create statement", e);
        }
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery(query);
            var name = resultSet.getString("product_name");
            var price = resultSet.getBigDecimal("price");
            var product = new Product(id, name, price);
            return Optional.of(product);
        } catch (SQLException e) {
            LOGGER.error("Can't create statement", e);
            throw new DataProcessingException("Can't create statement", e);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                var id = resultSet.getLong("product_id");
                var name = resultSet.getString("product_name");
                var price = resultSet.getBigDecimal("price");
                var product = new Product(id, name, price);
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            LOGGER.error("Can't create statement", e);
            throw new DataProcessingException("Can't create statement", e);
        }
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET product_name = ?, price = ? WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            if (statement.executeUpdate() == 0) {
                LOGGER.error("Updating product failed, no rows affected");
            }
        } catch (SQLException e) {
            LOGGER.error("Can't create statement", e);
            throw new DataProcessingException("Can't create statement", e);
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                LOGGER.info("Deleting  product failed, no rows affected");
                return false;
            }
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't create statement", e);
            throw new DataProcessingException("Can't create statement", e);
        }
    }
}
