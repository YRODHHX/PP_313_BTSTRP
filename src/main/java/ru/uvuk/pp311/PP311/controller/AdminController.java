package ru.uvuk.pp311.PP311.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.uvuk.pp311.PP311.model.User;
import ru.uvuk.pp311.PP311.service.RoleService;
import ru.uvuk.pp311.PP311.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String showAllUsers(Model model, @AuthenticationPrincipal User authenticatedUser) {
        model.addAttribute("users", userService.showAllUsers());
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("user", userService.showUserById(authenticatedUser.getId()));
        return "admin/user-list"; // Возвращаем шаблон
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user-create")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/user-create";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/user-create")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit_user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.showUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/user-edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/edit_user/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
