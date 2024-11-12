package ru.uvuk.pp311.PP311.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.uvuk.pp311.PP311.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userProfile(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userService.findUserByUsername(username));
        model.addAttribute("role",userService.findUserByUsername(username).getRoles());
        return "user/profile";
    }

}


