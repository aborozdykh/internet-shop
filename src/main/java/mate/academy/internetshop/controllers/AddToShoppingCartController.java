package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;

public class AddToShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        String productId = req.getParameter("id");
        shoppingCartService.addProduct(shoppingCart,
                productService.get(Long.valueOf(productId)));
        resp.sendRedirect(req.getContextPath() + "/products/all");
    }
}