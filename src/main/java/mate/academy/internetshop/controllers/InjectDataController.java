package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    UserService userService = (UserService) injector.getInstance(UserService.class);
    ProductService productService = (ProductService) injector.getInstance(ProductService.class);
    ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var user1 = new User("Ferdinand", "ferdi", "nando");
        var user2 = new User("CherniyPlash", "uzhas", "letyashiy_na_kryliah_nochi");
        var user3 = new User("Praskoviya", "devushka", "iz_podmoskovya");
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);

        var product1 = new Product("Apple", new BigDecimal(1000));
        var product2 = new Product("Nokia", new BigDecimal(100));
        var product3 = new Product("HTC", new BigDecimal(10));
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        var cart1 = new ShoppingCart(user1);
        ShoppingCart shoppingCart = shoppingCartService.create(cart1);
        //        shoppingCartService.addProduct(shoppingCart, product1);
        //        shoppingCartService.addProduct(shoppingCart, product2);
        //        shoppingCartService.addProduct(shoppingCart, product3);

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
