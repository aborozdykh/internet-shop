package mate.academy.internetshop.controllers.shoppingcart;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class CompleteOrderController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private static OrderService orderService
            = (OrderService) INJECTOR.getInstance(OrderService.class);
    private static UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        var shoppingCart = shoppingCartService.getByUserId(userId);
        var user = userService.get(shoppingCart.getUserId());
        orderService.completeOrder(shoppingCart.getProducts(), user);
        shoppingCartService.clear(shoppingCart);
        resp.sendRedirect(req.getContextPath() + "/shoppingcart");
    }
}
