package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long orderId;
    private List<Product> products = new ArrayList<>();
    private Long userId;

    public Order(Long orderId, Long userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public Order(List<Product> products, User user) {
        this.products = products;
        this.userId = user.getUserId();
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

    public Long getUserId() {
        return userId;
    }

    public void setUser(User user) {
        this.userId = user.getUserId();
    }

    @Override
    public String toString() {
        return "Order{"
                + "orderId=" + orderId
                + ", products=" + products
                + ", user=" + userId
                + '}';
    }
}
