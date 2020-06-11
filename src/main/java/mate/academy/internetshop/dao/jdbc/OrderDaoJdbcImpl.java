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
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class OrderDaoJdbcImpl extends GenericImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(User user) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't user's orders", e);
        }
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setOrderId(generatedKeys.getLong(1));
            }
            addProductsToOrder(order);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order", e);
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var order = getOrderFromResultSet(resultSet);
                return Optional.of(order);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order", e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * from orders";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                orderList.add(getOrderFromResultSet(resultSet));
            }
            return orderList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
    }

    @Override
    public Order update(Order order) {
        deleteProductsFromOrder(order);
        addProductsToOrder(order);
        return order;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        String deleteFromOrdersQuery = "DELETE FROM orders WHERE order_id = ?";
        String deleteFromOrdersProductsQuery = "DELETE FROM orders_products WHERE order_id = ?";
        deleteByQuery(deleteFromOrdersProductsQuery, id);
        return deleteByQuery(deleteFromOrdersQuery, id);
    }

    private Order getOrderFromResultSet(ResultSet resultSet) {
        try {
            Long orderId = resultSet.getLong("order_id");
            Long userId = resultSet.getLong("user_id");
            var order = new Order(orderId, userId);
            order.setProducts(getProductsFromOrder(order));
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get products from order", e);
        }
    }

    private List<Product> getProductsFromOrder(Order order) {
        String query = "SELECT p.product_id, p.product_name, p.price "
                + "FROM orders_products op "
                + "JOIN products p "
                + "ON op.product_id = p.product_id "
                + "WHERE op.order_id = ?;";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, order.getOrderId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("product_name");
                BigDecimal price = resultSet.getBigDecimal("price");
                products.add(new Product(productId, name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get products from order", e);
        }
    }

    private void addProductsToOrder(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Product product : order.getProducts()) {
                String query = "INSERT INTO orders_products(order_id, product_id) "
                        + "VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, order.getOrderId());
                statement.setLong(2, product.getProductId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to order", e);
        }
    }

    private void deleteProductsFromOrder(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM orders_products WHERE order_id  = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, order.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from order", e);
        }
    }
}
