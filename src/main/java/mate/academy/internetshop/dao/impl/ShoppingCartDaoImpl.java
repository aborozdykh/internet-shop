package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.model.ShoppingCart;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(currentCartId -> shoppingCart.getShoppingCartId()
                        .equals(Storage.shoppingCarts.get(currentCartId).getShoppingCartId()))
                .forEach(i -> Storage.shoppingCarts.set(i, shoppingCart));
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long shoppingCartId) {
        return Storage.shoppingCarts
                .stream()
                .filter(shoppingCart -> shoppingCart.getShoppingCartId().equals(shoppingCartId))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public boolean delete(Long shoppingCartId) {
        return Storage.shoppingCarts.removeIf(shoppingCart
                -> shoppingCart.getShoppingCartId().equals(shoppingCartId));
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return getAll()
                .stream()
                .filter(shoppingCart -> shoppingCart.getUserId().equals(userId))
                .findFirst()
                .get();
    }
}
