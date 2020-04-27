package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
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
    public Optional<Order> get(Long orderId) {
        return Storage.orders
                .stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public boolean delete(Long orderId) {
        return Storage.orders.removeIf(order -> order.getOrderId().equals(orderId));
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return getAll()
                .stream()
                .filter(order -> order.getUser().getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
    }
}
