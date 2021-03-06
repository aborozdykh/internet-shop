package mate.academy.internetshop.dao.jdbc;

import java.math.BigDecimal;
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

@Dao
public class ProductDaoJdbcImpl extends GenericImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (product_name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setProductId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create product", e);
        }
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var user = getProductFromResultSet(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product", e);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
            }
            return productList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all products", e);
        }
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET product_name = ?, price = ? WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product", e);
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        String deleteFromProductsQuery = "DELETE FROM products WHERE product_id = ?";
        String deleteFromPOrdersProductsQuery = "DELETE FROM orders_products WHERE product_id = ?";
        String deleteFromShoppingCartsProductsQuery
                = "DELETE FROM shopping_carts_products WHERE product_id = ?";
        deleteByQuery(deleteFromPOrdersProductsQuery, id);
        deleteByQuery(deleteFromShoppingCartsProductsQuery, id);
        return deleteByQuery(deleteFromProductsQuery, id);
    }

    public Product getProductFromResultSet(ResultSet resultSet) {
        try {
            long id = resultSet.getLong("product_id");
            String name = resultSet.getString("product_name");
            BigDecimal price = resultSet.getBigDecimal("price");
            return new Product(id, name, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product", e);
        }
    }
}
