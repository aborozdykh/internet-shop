package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class GetAllOrdersController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private OrderService orderService = (OrderService) INJECTOR.getInstance(OrderService.class);
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long user_id = (Long) req.getSession().getAttribute(USER_ID);
        List<Order> allOrders = orderService.getUserOrders(userService.get(user_id));
        req.setAttribute("orders", allOrders);
        req.getRequestDispatcher("/WEB-INF/views/orders/all.jsp").forward(req, resp);
    }
}
