package ru.khusnullin.SpringBootUpdate2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.khusnullin.SpringBootUpdate2.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String printUser(ModelMap model, Principal principal) {
        Long id = userService.getUserByEmail(principal.getName()).getId();
        model.addAttribute("user", userService.getUserById(id));
        return "users/user";
    }

}
