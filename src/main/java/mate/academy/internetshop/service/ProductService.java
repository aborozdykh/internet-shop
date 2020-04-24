package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.model.Product;

public interface ProductService {
    Product create(Product product);

    Product getProduct(Long productId);

    List<Product> getAllProducts();

    Product update(Product product);

    boolean delete(Long productId);

    boolean delete(Product product);
}
