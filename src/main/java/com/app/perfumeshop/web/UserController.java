package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.UserRegisterDTO;
import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ModelAttribute("userModel")
    public UserRegisterDTO initUserModel() {
        return new UserRegisterDTO();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailure(
            @ModelAttribute("email") String email,
            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", "true");

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String confirmRegister(@Valid UserRegisterDTO userModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:/users/register";

        }

        this.userService.registerUser(userModel);

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String myProfile(Model model,
                            Principal principal) {

        model.addAttribute("userProfile", userService.findByEmail(principal.getName()));
//        model.addAttribute(COUNT_PRODUCTS,
//                this.userService
//                        .getUserByUsername(principal.getName()).getCart()
//                        .getCountProducts());

        return "user-profile";
    }

    @GetMapping("/details-profile/{id}")
    public String viewUserProfileById(@PathVariable("id") Long id,
                                      Model model, Principal principal) {

        UserViewDTO userProfile = userService.findUserById(id);

        model.addAttribute("userProfile", userProfile);

        return "user-profile";
    }

}
