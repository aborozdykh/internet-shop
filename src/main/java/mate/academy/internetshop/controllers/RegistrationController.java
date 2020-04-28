package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("pwd");
        String repeatPassword = req.getParameter("pwd-repeat");
        req.setAttribute("name", name);
        req.setAttribute("login", login);
        if (!password.equals(repeatPassword)) {
            req.setAttribute("messageDifferentPassword",
                    "Passwords are different. Please put same passwords!");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        } else {
            userService.create(new User(name, login, password));
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}

