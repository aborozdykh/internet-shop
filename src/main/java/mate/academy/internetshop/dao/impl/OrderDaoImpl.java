package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(List<Product> products, User user) {
        Order order = new Order(List.copyOf(products), user);
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(currentOrder ->
                        order.getOrderId().equals(Storage.orders.get(currentOrder).getOrderId()))
                .forEach(i -> Storage.orders.set(i, order));
        return null;
    }

    @Override
    public Optional<Order> getOrder(Long orderId) {
        return Storage.orders
                .stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();
    }

    @Override
    public Optional<Order> getOrder(Order order) {
        return getOrder(order.getOrderId());
    }

    @Override
    public List<Order> getAllOrders() {
        return Storage.orders;
    }

    @Override
    public boolean delete(Long orderId) {
        return Storage.orders.removeIf(order -> order.getOrderId().equals(orderId));
    }
}
