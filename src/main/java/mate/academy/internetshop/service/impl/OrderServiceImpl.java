package mate.academy.internetshop.service.impl;

import java.util.List;
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
        return orderDao.create(new Order(products, user));
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getUserOrders(user);
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
