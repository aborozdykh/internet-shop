package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long orderId;
    private List<Product> products = new ArrayList<>();
    private User user;

    public Order() {
    }

    public Order(List<Product> products, User user) {
        this.products = products;
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{"
                + "orderId=" + orderId
                + ", products=" + products
                + ", user=" + user
                + '}';
    }
}
