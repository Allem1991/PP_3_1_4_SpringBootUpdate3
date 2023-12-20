package ru.khusnullin.SpringBootUpdate2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.khusnullin.SpringBootUpdate2.model.Role;
import ru.khusnullin.SpringBootUpdate2.model.User;
import ru.khusnullin.SpringBootUpdate2.service.RoleService;
import ru.khusnullin.SpringBootUpdate2.service.UserService;
import ru.khusnullin.SpringBootUpdate2.utils.RoleConverter;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new RoleConverter(roleService));
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
    public String getCreatePage(@ModelAttribute("user") User user, ModelMap model) {
        List<Role> allRoles = roleService.getRoles();
        model.addAttribute("allRoles", allRoles);
        return "admins/create";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            List<Role> allRoles = roleService.getRoles();
            model.addAttribute("allRoles", allRoles);
            return "admins/create";
        }
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/delete", params = "id")
    public String deleteUser(@RequestParam(value = "id", required = false) long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/edit", params = "id")
    public String getEditPage(@RequestParam(value = "id", required = false) long id, ModelMap model) {
        List<Role> allRoles = roleService.getRoles();
        model.addAttribute("userEdit", userService.getUserById(id));
        model.addAttribute("allRoles", allRoles);
        return "admins/edit";
    }

    @PostMapping(params = "id")
    public String editUser(@RequestParam(value = "id", required = false) long id, ModelMap model,
                           @ModelAttribute("userEdit") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Role> allRoles = roleService.getRoles();
            model.addAttribute("allRoles", allRoles);
            return "admins/edit";
        }
        userService.updateUser(user);
        return "redirect:/admin/users?id=" + id;
    }

}
