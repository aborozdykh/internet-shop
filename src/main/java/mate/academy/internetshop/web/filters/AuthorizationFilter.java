package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private Map<String, List<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/admin/users/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/users/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/products/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/products/add", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/products/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/shoppingcart", List.of(Role.RoleName.USER, Role.RoleName.ADMIN));
        protectedUrls.put("/shoppingcart/products/add", List.of(Role.RoleName.USER,
                Role.RoleName.ADMIN));
        protectedUrls.put("/shoppingcart/products/delete", List.of(Role.RoleName.USER,
                Role.RoleName.ADMIN));
        protectedUrls.put("/orders/complete", List.of(Role.RoleName.USER, Role.RoleName.ADMIN));
        protectedUrls.put("/orders/show", List.of(Role.RoleName.USER, Role.RoleName.ADMIN));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestedUrl = req.getServletPath();

        if (protectedUrls.get(requestedUrl) == null) {
            chain.doFilter(req, resp);
            return;
        }

        var userId = (Long) req.getSession().getAttribute(USER_ID);
        if (userId == null || userService.get(userId) == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        var user = userService.get(userId);
        if (isAuthorized(user, protectedUrls.get(requestedUrl))) {
            chain.doFilter(req, resp);
        } else {
            LOGGER.warn("User " + user.getLogin() + " forbidden access to page " + requestedUrl);
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
        return;
    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(User user, List<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
