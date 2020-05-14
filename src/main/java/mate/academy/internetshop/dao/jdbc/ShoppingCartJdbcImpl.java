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
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ShoppingCartJdbcImpl implements ShoppingCartDao {


    @Override
    public ShoppingCart getByUserId(Long userId) {
        String query = "SELECT * FROM shopping_carts WHERE user_id = ?";
        var shoppingCart = new ShoppingCart(userId);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                shoppingCart = getShoppingCartFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart by user ID", e);
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                shoppingCart.setShoppingCartId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create shopping cart", e);
        }
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var shoppingCart = getShoppingCartFromResultSet(resultSet);
                return Optional.of(shoppingCart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart", e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * from shopping_carts";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            while (resultSet.next()) {
                shoppingCarts.add(getShoppingCartFromResultSet(resultSet));
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all shopping carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        deleteProductsFromCart(shoppingCart);
        addProductsToCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM shopping_carts WHERE shopping_cart_id = ?";
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

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) {
        try {
            Long shoppingCartId = resultSet.getLong("shopping_cart_id");
            Long userId = resultSet.getLong("user_id");
            var shoppingCart = new ShoppingCart(shoppingCartId, userId);
            shoppingCart.setProducts(getProductsFromShoppingCart(shoppingCart));
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get products from ShoppingCart", e);
        }
    }

    private List<Product> getProductsFromShoppingCart(ShoppingCart shoppingCart) {
        String query = "SELECT p.product_id, p.product_name, p.price "
                + "FROM shopping_carts_products scp "
                + "JOIN products p "
                + "ON  scp.product_id=p.product_id "
                + "WHERE scp.shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            List<Product> products = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCart.getShoppingCartId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("product_name");
                BigDecimal price = resultSet.getBigDecimal("price");
                products.add(new Product(productId, name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get products from shopping cart", e);
        }
    }

    private void addProductsToCart(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Product product : shoppingCart.getProducts()) {
                String query = "INSERT INTO shopping_carts_products(shopping_cart_id, product_id) "
                        + "VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, shoppingCart.getShoppingCartId());
                statement.setLong(2, product.getProductId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to shopping cart", e);
        }
    }

    private void deleteProductsFromCart(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM shopping_carts_products WHERE shopping_cart_id=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCart.getShoppingCartId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from shopping cart", e);
        }
    }
}

