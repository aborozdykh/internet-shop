package mate.academy.internetshop.controllers.order;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.OrderService;

public class GetOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static OrderService orderService
            = (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("id");
        List<Product> productsInOrder = orderService.get(Long.valueOf(orderId)).getProducts();
        req.setAttribute("products", productsInOrder);
        req.getRequestDispatcher("/WEB-INF/views/orders/show.jsp").forward(req, resp);
    }
}
