package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.util.ConnectionUtil;

public class ShoppingCartJdbcImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart getByUserId(Long userId) {
        return null;
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, shoppingCart.getUser().getUserId());
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
        return Optional.empty();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return null;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
