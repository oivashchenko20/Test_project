package com.example.controllers;

import com.example.domain.Message;
import com.example.domain.User;
import com.example.repos.MessageRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
/**
 * This controller can show all information
 * on mainpage and manipulate with articles
 *
 * @author Oleksandr_Ivaschenko
 */

@Controller
public class MainController {

    @Autowired
    private MessageRepos messageRepos;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/mainpage")
    public String showMainPage(Model model, @AuthenticationPrincipal User user) {

        Iterable<Message> messages = messageRepos.findAll();

        model.addAttribute("user", user);
        model.addAttribute("messages", messages);

        return "mainpage";
    }

    @GetMapping("/mainpage/details/{message}")
    public String detailsMessage(@PathVariable Message message, @AuthenticationPrincipal User user, Model model) {

        model.addAttribute("messages", message);
        model.addAttribute("user", user);

        return "detail";
    }

    @PostMapping("/mainpage/details/{message}")
    public String deleteDetailsMessage(@PathVariable Message message, Model model) {

        messageRepos.delete(message);

        return "redirect:/mainpage";
    }

    @GetMapping("/add")
    public String Message() {
        return "addMessage";
    }

    @PostMapping("/add")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String title,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Message message = new Message(text, user, title);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }

        messageRepos.save(message);

        return "addMessage";
    }

}
