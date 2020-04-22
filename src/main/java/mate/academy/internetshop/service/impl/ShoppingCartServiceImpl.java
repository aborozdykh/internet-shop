package mate.academy.internetshop.service.impl;

import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Dao
    private ShoppingCartDao shoppingCartDao;

    @Dao
    private ProductDao productDao;

    @Override
    public ShoppingCart addProduct(Long shoppingCartId, Long productId) {
        /*
            ShoppingCart shoppingCart = shoppingCartDao.get(bucketId);
            Optional<Product> item = productDao.get(itemId);
            shoppingCart.getProducts().add(item);
            return shoppingCartDao.update(shoppingCart);
        */
        return null;
    }
}
