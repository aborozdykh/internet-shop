package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    OrderDao orderDao;

    @Inject
    ShoppingCartService shoppingCartService;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order completeOrder(List<Product> products, User user) {
        Order order = orderDao.create();
        order.setProducts(List.copyOf(products));
        order.setUser(user);
        shoppingCartService.clear(shoppingCartService.getByUserId(user.getUserId()));
        return order;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getAll()
                .stream()
                .filter(order -> order.getUser().getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order get(Long orderId) {
        return orderDao.get(orderId).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Long orderId) {
        return orderDao.delete(orderId);
    }
}
