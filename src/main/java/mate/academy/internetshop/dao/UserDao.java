package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.User;

public interface UserDao {
    User create(User user);

    Optional<User> getUser(Long userId);

    List<User> getAllUsers();

    User update(User user);

    boolean delete(Long userId);
}
