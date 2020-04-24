package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;

public interface OrderDao {
    Order create(List<Product> products, User user);

    Order update(Order order);

    Optional<Order> getOrder(Long orderId);

    Optional<Order> getOrder(Order order);

    List<Order> getAllOrders();

    boolean delete(Long orderId);
}
