package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.service.UserRoleService;
import com.app.perfumeshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public AdminController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/users/all")
    public String getAllUsers(Model model) {

        List<UserViewDTO> users = userService.getAllUser();

        model.addAttribute("users", users);

        return "users-all";
    }

    @GetMapping("/users/change-role/{id}")
    public String changeUserRoleGet(@PathVariable("id") Long id,
                                    Model model) {

        UserViewDTO user = userService.findUserById(id);

        model.addAttribute("user" , user);

        return "user-change-role";
    }

    @PatchMapping("/users/roles/remove/{id}")
    public String removeRole(@PathVariable("id") Long id) {

        this.userRoleService.removeRole(id);

        return "redirect:/users/change-role/{id}";
    }

    @PatchMapping("/users/roles/add/{id}")
    public String addRole(@PathVariable("id") Long id) {

        this.userRoleService.addRole(id);

        return "redirect:/users/change-role/{id}";
    }
}
