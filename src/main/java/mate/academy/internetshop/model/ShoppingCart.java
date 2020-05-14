package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long shoppingCartId;
    private List<Product> products = new ArrayList<>();
    private Long userId;

    public ShoppingCart() {
    }

    public ShoppingCart(Long userId) {
        this.userId = userId;
    }

    public ShoppingCart(Long shoppingCartId, User user) {
        this.shoppingCartId = shoppingCartId;
        this.userId = user.getUserId();
    }

    public ShoppingCart(Long shoppingCartId, Long userId) {
        this.shoppingCartId = shoppingCartId;
        this.userId = userId;
    }

    public ShoppingCart(List<Product> products, User user) {
        this.products = products;
        this.userId = user.getUserId();
    }

    public ShoppingCart(User user) {
        products = new ArrayList<>();
        this.userId = user.getUserId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUser(User user) {
        this.userId = user.getUserId();
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "shoppingCartId=" + shoppingCartId
                + ", products=" + products
                + ", user=" + userId
                + '}';
    }
}
