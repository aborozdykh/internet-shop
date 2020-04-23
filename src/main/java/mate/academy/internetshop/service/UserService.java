package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.model.User;

public interface UserService {
    User create(User user);

    User getUser(Long userId);

    List<User> getAllUsers();

    User update(User user);

    boolean delete(Long userId);
}
