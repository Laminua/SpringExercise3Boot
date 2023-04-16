package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.security.UserProfileDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class UserController {

    @RequestMapping("/welcome")
    public String showWelcomePage(@AuthenticationPrincipal UserProfileDetails userProfile, Model model) {
        log.info("Welcome page for access level \"USER\" has been requested");

        model.addAttribute("userProfile", userProfile.getUserProfile());

        return "welcome";
    }
}