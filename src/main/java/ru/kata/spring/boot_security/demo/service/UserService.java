package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private UserDao userDao;

    private PasswordEncoder passwordEncoder;

    private RoleService roleService;

    @Autowired
    public UserService(UserDao userDao, @Lazy PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, NullPointerException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found" + username);
        }
        return new org.springframework.security.core.userdetails.
                User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public User findById(Long id) {
        return userDao.getOne(id);
    }

    public User registerNewAccount(User user ,List<Long> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.findByIdRoles(roles));
        return userDao.save(user);
    }
    public User editAccount(User user ,List<Long> roles) {
        if (userDao.findByUsername(user.getUsername()) == null ||
                !user.getPassword().equals(userDao.findByUsername(user.getUsername()).getPassword())) {
            if (userDao.findByUsername(user.getUsername()) == null) {
                userDao.save(user);
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userDao.save(user);
            }
        }
        user.setRoles(roleService.findByIdRoles(roles));
        return userDao.save(user);
    }
}
