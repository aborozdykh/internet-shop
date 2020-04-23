package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getShoppingCart(Long shoppingCartId);

    Optional<ShoppingCart> getShoppingCart(ShoppingCart shoppingCart);

    List<ShoppingCart> getAllShoppingCarts();

    boolean delete(Long shoppingCartId);
}
