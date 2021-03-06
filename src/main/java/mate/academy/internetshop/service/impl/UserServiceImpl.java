package mate.academy.internetshop.service.impl;

import static mate.academy.internetshop.util.HashUtil.getSalt;
import static mate.academy.internetshop.util.HashUtil.hashPassword;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        user.setSalt(getSalt());
        String hashPassword = hashPassword(user.getPassword(), user.getSalt());
        user.setPassword(hashPassword);
        return userDao.create(user);
    }

    @Override
    public User get(Long userId) {
        return userDao.get(userId).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long userId) {
        return userDao.delete(userId);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
