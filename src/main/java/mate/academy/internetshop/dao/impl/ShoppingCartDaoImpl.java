package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.ShoppingCart;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0,Storage.shoppingCarts.size())
                .filter(currentCartId -> shoppingCart.getShoppingCartId()
                        .equals(Storage.shoppingCarts.get(currentCartId).getShoppingCartId()));
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getShoppingCart(Long shoppingCartId) {
        return Storage.shoppingCarts
                .stream()
                .filter(shoppingCart -> shoppingCart.getShoppingCartId().equals(shoppingCartId))
                .findFirst();
    }

    @Override
    public Optional<ShoppingCart> getShoppingCart(ShoppingCart shoppingCart) {
        return getShoppingCart(shoppingCart.getShoppingCartId());
    }

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        return Storage.shoppingCarts;
    }

    @Override
    public boolean delete(Long shoppingCartId) {
        return Storage.shoppingCarts.removeIf(shoppingCart
                -> shoppingCart.getShoppingCartId().equals(shoppingCartId));
    }
}
