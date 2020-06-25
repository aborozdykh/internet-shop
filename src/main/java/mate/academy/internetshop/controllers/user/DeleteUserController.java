package mate.academy.internetshop.controllers.user;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class DeleteUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private static ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private static OrderService orderService
            = (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long userId = Long.valueOf(req.getParameter("id"));
        Long shoppingCartId = shoppingCartService.getByUserId(userId).getShoppingCartId();
        shoppingCartService.delete(shoppingCartId);
        List<Order> orderList = orderService.getUserOrders(userService.get(userId));
        for (Order order : orderList) {
            orderService.delete(order.getOrderId());
        }
        userService.delete(userId);
        resp.sendRedirect(req.getContextPath() + "/admin/users/all");
    }
}
