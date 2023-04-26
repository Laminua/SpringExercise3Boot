package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.models.UserProfile;
import com.example.springexercise3boot.security.UserProfileDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("/welcome")
    public ModelAndView showWelcomePage() {
        log.info("Welcome page for access level \"USER\" has been requested");

        return new ModelAndView("welcome");
    }

    @GetMapping("/getUserInfo")
    public UserProfile getUserInfo(@AuthenticationPrincipal UserProfileDetails userProfile) {
        log.info("Authenticated user profile details requested");

        return userProfile.getUserProfile();
    }
}