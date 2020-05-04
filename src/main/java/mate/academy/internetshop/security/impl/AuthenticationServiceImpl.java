package mate.academy.internetshop.security.impl;

import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.security.AuthenticationService;
import mate.academy.internetshop.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    public static final String INCORRECT_LOGIN_OR_PASSWORD = "Enter valid login and password.";
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        var userFromDB = userService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException(INCORRECT_LOGIN_OR_PASSWORD));
        if (userFromDB.getPassword().equals(password)) {
            return userFromDB;
        }
        throw new AuthenticationException(INCORRECT_LOGIN_OR_PASSWORD);
    }
}
