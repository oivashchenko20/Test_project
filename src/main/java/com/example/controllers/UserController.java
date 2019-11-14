package com.example.controllers;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repos.UserRepos;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

/**
 * This controller show all manipulate with user
 * and all functions of UserProfile page
 *
 * @author Oleksandr_Ivaschenko
 */
@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepos userRepos;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {

        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{user}")
    public String deleteUser(@PathVariable User user, RedirectAttributes attributes) {

        if (user.getRoles().contains(Role.ADMIN)) {
            attributes.addFlashAttribute("error", "You can delete ADMIN!");
        } else {
            userRepos.deleteById(user.getId());
        }

        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEdit(@PathVariable User user, Model model) {

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save/{user}")
    public String userSave(
            @RequestParam Map<String, String> form,
            @PathVariable User user) {

        userService.saveUser(user, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {

        model.addAttribute("user", user);

        return "editProfile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal @Valid User user,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "mobile", required = false) String mobile
    ) {

        if (!firstName.matches("^[a-zA-Z]{2,}$")) {
            return "redirect:/user/profile";
        }
        if (!lastName.matches("^[a-zA-Z]{2,}$")) {
            return "redirect:/user/profile";
        }
        if (!mobile.matches("(?:\\d{3}-){2}\\d{4}")) {
            return "redirect:/user/profile";
        }
        if (password.length() > 15) {
            return "redirect:/user/profile";
        }

        userService.updateProfile(user, password, email, firstName, lastName, mobile);

        return "redirect:/login";
    }

    @GetMapping("info")
    public String profile(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("user", user);
        return "userProfile";
    }

}
