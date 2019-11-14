package com.example.controllers;

import com.example.domain.User;
import com.example.repos.UserRepos;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

/**
 * This controller show all function of
 * registration and forgotPassword pages
 *
 * @author Oleksandr_Ivaschenko
 */

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepos userRepos;

    @GetMapping("/registration")
    public String registration(Model model, User user) {

        model.addAttribute("user", user);

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

        User user1 = userRepos.findByEmail(user.getEmail());

        if (user1 != null) {
            model.addAttribute("alreadyRegisteredMessage", "There is already register user with this email");
            return "registration";
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords are not equal");
            return "registration";
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exist!");
            return "registration";
        }

        model.addAttribute("message", "Registration complete, please checked your email ");

        return "answer";
    }

    @GetMapping("/answer")
    public String answer() {
        return "answer";
    }

    @GetMapping("/password")
    public String getPassword() {
        return "forgotPassword";
    }

    @PostMapping("/password")
    public String getNewPassword(
            User user,
            @RequestParam String email,
            Model model
    ) {
        userService.setNewPassword(user, email);
        model.addAttribute("messages", "Password changed, please checked your gmail");
        return "forgotPassword";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User is successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }


}
