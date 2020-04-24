package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartService extends GenericService<ShoppingCart, Long> {

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    List<Product> getAllProducts(ShoppingCart shoppingCart);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart); //remove all products from the shoppingCart

    ShoppingCart getByUserId(Long userId);
}
