package mate.academy.internetshop;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        var productService = (ProductService) injector.getInstance(ProductService.class);

        /// Products test travis
        var product1 = new Product("Apple", new BigDecimal(1000));
        var product2 = new Product("Nokia", new BigDecimal(100));
        var product3 = new Product("HTC", new BigDecimal(10));
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        System.out.println(productService.getAll());
        System.out.println(productService.get(2L));
        System.out.println("" + productService.delete((long) 5));
        System.out.println(productService.getAll());

        var userService = (UserService) injector.getInstance(UserService.class);

        /// Users
        System.out.println("========================== Users ==========================");
        var user1 = new User("Ferdinand", "ferdi", "nando");
        var user2 = new User("CherniyPlash", "uzhas", "letyashiy_na_kryliah_nochi");
        var user3 = new User("Praskoviya", "devushka", "iz_podmoskovya");
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        System.out.println(userService.getAll());
        System.out.println("Get user by id 2: " + userService.get(2L));
        System.out.println("Delete by wrong id 5: " + userService.delete((long) 5));
        System.out.println("Delete by id 1: " + userService.delete((long) 1));
        System.out.println(userService.getAll());

        var shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

        /// Carts
        System.out.println("========================== Carts ==========================");
        var cart2 = new ShoppingCart(user2);
        ShoppingCart shoppingCart = shoppingCartService.create(cart2);
        shoppingCartService.addProduct(shoppingCart, product1);
        shoppingCartService.addProduct(shoppingCart, product2);
        shoppingCartService.addProduct(shoppingCart, product3);
        System.out.println("User2's cart: " + shoppingCartService.getByUserId(2L));
        System.out.println("All products before delete: "
                + shoppingCartService.getAllProducts(shoppingCart));

        /// Orders
        System.out.println("========================== Orders ==========================");
        System.out.println("User with id 2: " + userService.get(2L));
        System.out.println("User2's cart: " + shoppingCartService.getByUserId(2L));
        System.out.println("All products in user2's cart: "
                + shoppingCartService.getAllProducts(shoppingCart));

        var order = new Order(List.copyOf(shoppingCart.getProducts()), shoppingCart.getUser());

        var orderService = (OrderService) injector.getInstance(OrderService.class);

        orderService.completeOrder(shoppingCart.getProducts(), shoppingCart.getUser());
        System.out.println("All products in cart of user2after order created: "
                + shoppingCartService.getAllProducts(shoppingCart));
        System.out.println("Orders of user2: " + orderService.getUserOrders(user2));
    }
}
