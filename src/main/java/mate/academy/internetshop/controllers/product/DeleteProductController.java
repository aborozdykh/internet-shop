package mate.academy.internetshop.controllers.product;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.ProductService;

public class DeleteProductController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String productId = req.getParameter("id");
        productService.delete(Long.valueOf(productId));
        resp.sendRedirect(req.getContextPath() + "/admin/products/all");
    }
}
