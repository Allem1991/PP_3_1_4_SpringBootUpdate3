package ru.khusnullin.SpringBootUpdate2.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khusnullin.SpringBootUpdate2.model.User;
import ru.khusnullin.SpringBootUpdate2.service.UserService;


@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String printAllUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "admins/allUsers";
    }

    @GetMapping(params = "id")
    public String printUser(@RequestParam(value = "id", required = false) long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admins/user";
    }

    @GetMapping("/new")
    public String getCreatePage(@ModelAttribute("user") User user) {
        return "admins/create";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam(name = "roleAdmin", required = false) String roleAdmin) {
        if (bindingResult.hasErrors()) {
            return "admins/create";
        }
        userService.addUserByAdmin(user, roleAdmin);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/delete", params = "id")
    public String deleteUser(@RequestParam(value = "id", required = false) long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/edit", params = "id")
    public String getEditPage(@RequestParam(value = "id", required = false) long id, ModelMap model) {
        model.addAttribute("userEdit", userService.getUserById(id));
        return "admins/edit";
    }

    @PostMapping(params = "id")
    public String editUser(@RequestParam(value = "id", required = false) long id,
                           @ModelAttribute("userEdit") @Valid User user, BindingResult bindingResult,
                           @RequestParam(name = "roleAdmin", required = false) String roleAdmin) {
        if (bindingResult.hasErrors()) {
            return "admins/edit";
        }
        userService.updateUserByAdmin(user, roleAdmin);
        return "redirect:/admin/users?id=" + id;
    }

}
