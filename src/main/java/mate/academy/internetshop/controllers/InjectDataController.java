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
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var bob = new User("Bob", "bob", "1");
        bob.setRoles(Set.of(Role.of("USER")));
        var alisa = new User("Alisa", "alisa", "1");
        alisa.setRoles(Set.of(Role.of("USER")));
        var admin = new User("Admin", "admin", "1");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(bob);
        userService.create(alisa);
        userService.create(admin);

        var product1 = new Product("Apple", new BigDecimal(1000));
        var product2 = new Product("Nokia", new BigDecimal(100));
        var product3 = new Product("HTC", new BigDecimal(10));
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
