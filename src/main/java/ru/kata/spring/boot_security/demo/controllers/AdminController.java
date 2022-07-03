package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Scanner;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
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
    public String createUser(User user){
        userService.saveUser(user);
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
    public String update (User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

}
