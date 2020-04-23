package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Product;

public interface ProductDao {

    Product create(Product product);

    Optional<Product> get(Long productId);

    List<Product> getAllProducts();

    Product update(Product product);

    boolean delete(Long productId);

    boolean delete(Product product);
}
