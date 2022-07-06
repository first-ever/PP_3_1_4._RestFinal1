package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;

    private RoleService roleService;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String goToHomePage() {
        return "home";
    }

    @GetMapping("/admin")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "admin";
    }

    @GetMapping("/admin/create")
    public String createUserForm(User user) {
        return "create";
    }

    @PostMapping("/admin/create")
    public String createUser(@RequestParam("role") ArrayList<Long> roles,
                             @ModelAttribute("user") @Valid User user) {
        userService.registerNewAccount(user,roles);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/delete/{id}",method = RequestMethod.POST)
    public String deleteUser(@PathVariable("id")Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String updateUserForm(@PathVariable("id")Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "/edit";
    }

    @PostMapping("/admin/edit")
    public String updateUser (@RequestParam("role") ArrayList<Long> roles,
            @ModelAttribute("user") @Valid User user) {
        userService.editAccount(user,roles);
        return "redirect:/admin";
    }

}
