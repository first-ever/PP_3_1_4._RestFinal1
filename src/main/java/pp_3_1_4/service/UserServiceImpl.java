package pp_3_1_4.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp_3_1_4.DAO.UserDAO;
import pp_3_1_4.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, @Lazy PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUser(long id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (!user.getPassword().equals(userDAO.getById(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDAO.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDAO.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findUserByEmail(username);
    }
}
