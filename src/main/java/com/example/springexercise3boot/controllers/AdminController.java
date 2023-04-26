package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.dto.UserProfileDTO;
import com.example.springexercise3boot.models.UserProfile;
import com.example.springexercise3boot.services.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final UserProfileService userProfileService;

    @Autowired
    public AdminController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/index")
    public ModelAndView showAdminPage() {
        log.info("Showing admin page");

        return new ModelAndView("admin/index");
    }

    @GetMapping("/getUsers")
    public List<UserProfile> getUsers() {
        log.info("requested \"getUsersList\" endpoint");

        return userProfileService.findAll();
    }

    @GetMapping("/addUserForm")
    public ModelAndView showAddUserForm() {
        log.info("Showing \"Add user\" page");

        return new ModelAndView("admin/add-user");
    }

    @PostMapping("/addUser")
    public RedirectView addUser(@ModelAttribute UserProfileDTO profileDTO) {
        log.info("Inserting user into database. Login: " + profileDTO.getUsername()
                + " Role: " + profileDTO.getRole()
                + " Name: " + profileDTO.getName()
                + " Email: " + profileDTO.getEmail());

        if (profileDTO.getUsername().equals("")) {
            return new RedirectView("/");
        } else if (userProfileService.findByUsername(profileDTO.getUsername()) != null) {
            return new RedirectView("/admin/userExists");
        }

        userProfileService.save(convertToUserProfile(profileDTO));

        return new RedirectView("/admin/index");
    }

    @GetMapping("/userExists")
    public ModelAndView showUserExistsMessage() {
        log.info("Showing \"User already exist\" page");

        return new ModelAndView("admin/login-exists");
    }

    @GetMapping("/deleteUser")
    public RedirectView deleteUser(@RequestParam("userIdToDelete") int id) {
        log.info("Removing user from database, ID: " + id);

        userProfileService.delete(id);

        return new RedirectView("/admin/index");
    }

    @GetMapping("/updateUserForm")
    public ModelAndView showUpdateUserForm() {
        log.info("Showing \"update user\" page");

        return new ModelAndView("admin/update-user");
    }

    @GetMapping("/getUser/{id}")
    public UserProfile getUserProfileById(@PathVariable int id) {
        log.info("UserProfile with ID: " + id + " requested from DB");

        return userProfileService.findOne(id);
    }

    @PostMapping("/updateUser")
    public RedirectView updateUser(@ModelAttribute UserProfileDTO profileDTO) {
        log.info("Updating user in database. Name: " + profileDTO.getName() + " Email: " + profileDTO.getEmail());

        userProfileService.update(profileDTO.getId(), convertToUserProfile(profileDTO));

        return new RedirectView("/admin/index");
    }

    private UserProfile convertToUserProfile(UserProfileDTO profileDTO) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(profileDTO.getId());
        userProfile.setUsername(profileDTO.getUsername());
        userProfile.setPassword(profileDTO.getPassword());
        userProfile.setRole(profileDTO.getRole());
        userProfile.setName(profileDTO.getName());
        userProfile.setEmail(profileDTO.getEmail());

        return userProfile;
    }
}
