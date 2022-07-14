package pp_3_1_4.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pp_3_1_4.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUser(long id);


    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(long id);
}
