package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user1 = new User("Ferdinand", "ferdi", "nando");
        var user2 = new User("CherniyPlash", "uzhas", "letyashiy_na_kryliah_nochi");
        var user3 = new User("Praskoviya", "devushka", "iz_podmoskovya");
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
