package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private static ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private static ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var bob = new User("Bob", "bob", "bob");
        bob.setRoles(Set.of(Role.of("USER")));
        var alice = new User("Alice", "alice", "alice");
        alice.setRoles(Set.of(Role.of("USER")));
        var admin = new User("Admin", "admin", "admin");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(bob);
        userService.create(alice);
        userService.create(admin);

        var shoppingCartAdmin = new ShoppingCart(admin);
        shoppingCartService.create(shoppingCartAdmin);

        var shoppingCartBob = new ShoppingCart(bob);
        shoppingCartService.create(shoppingCartBob);

        var shoppingCartAlice = new ShoppingCart(alice);
        shoppingCartService.create(shoppingCartAlice);

        var appleProduct = new Product("iPhone", new BigDecimal(1000));
        var nokiaProduct = new Product("Nokia", new BigDecimal(100));
        var htcProduct = new Product("HTC", new BigDecimal(10));
        productService.create(appleProduct);
        productService.create(nokiaProduct);
        productService.create(htcProduct);

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
