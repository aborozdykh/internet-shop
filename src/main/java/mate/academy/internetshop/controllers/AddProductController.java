package mate.academy.internetshop.controllers;

import static mate.academy.internetshop.controllers.IndexController.isNullOrEmpty;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class AddProductController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/products/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String productPrice = req.getParameter("price");
        req.setAttribute("name", name);
        req.setAttribute("price", productPrice);
        BigDecimal price = new BigDecimal(productPrice);
        if (isNullOrEmpty(name, productPrice)) {
            req.setAttribute("messageEmptyData", "Please put all data!");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        } else {
            productService.create(new Product(name, price));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
