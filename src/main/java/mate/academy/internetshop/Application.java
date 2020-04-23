package mate.academy.internetshop;

import java.math.BigDecimal;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        UserService userService = (UserService) injector.getInstance(UserService.class);
        /// Products
        Product product1 = new Product("Apple", new BigDecimal(1000));
        Product product2 = new Product("Nokia", new BigDecimal(100));
        Product product3 = new Product("HTC", new BigDecimal(10));
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        System.out.println(productService.getAllProducts());
        System.out.println(productService.get(2L));
        productService.delete(product1);
        System.out.println("" + productService.delete((long) 5));
        System.out.println("" + productService.delete((long) 2));
        System.out.println(productService.getAllProducts());

        /// Users
        System.out.println("========================== Users ==========================");
        User user1 = new User("Ferdinand","ferdi","nando");
        User user2 = new User("CherniyPlash","uzhas","letyashiy_na_kryliah_nochi");
        User user3 = new User("Praskoviya","devushka","iz_podmoskovya");
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        System.out.println(userService.getAllUsers());
        System.out.println("Get user by id 2" + userService.get(2L));
        System.out.println("Delete by wrong id 5 " + userService.delete((long) 5));
        System.out.println("Delete by id 2 " + userService.delete((long) 2));
        System.out.println(userService.getAllUsers());
    }
}
