package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.dto.UserProfileDTO;
import com.example.springexercise3boot.models.UserProfile;
import com.example.springexercise3boot.services.UserProfileService;
import com.example.springexercise3boot.util.UserProfileValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final UserProfileService userProfileService;
    private final UserProfileValidator userProfileValidator;

    @Autowired
    public AdminController(UserProfileService userProfileService, UserProfileValidator userProfileValidator) {
        this.userProfileService = userProfileService;
        this.userProfileValidator = userProfileValidator;
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
    public ResponseEntity<String> addUser(@Valid UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {
        log.info("Attempt inserting user into database. Login: " + profileDTO.getUsername()
                + " Role: " + profileDTO.getRole()
                + " Name: " + profileDTO.getName()
                + " Email: " + profileDTO.getEmail());

        userProfileValidator.validate(profileDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("User can't be added because of invalid data");
            throw new BindException(bindingResult);
        }

        log.info("User successfully created");
        userProfileService.save(convertToUserProfile(profileDTO));

        return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam("userIdToDelete") int id) {
        log.info("Attempt removing user from database, ID: " + id);

        if (userProfileService.findOne(id) != null) {
            userProfileService.delete(id);
        }
        log.info("User successfully deleted");
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
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
    public ResponseEntity<String> updateUser(@Valid UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {
        log.info("Attempt to update user in database. Name: " + profileDTO.getName() + " Email: " + profileDTO.getEmail());

        userProfileValidator.validate(profileDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("User can't be updated because of invalid data");
            throw new BindException(bindingResult);
        }

        log.info("User successfully updated");
        userProfileService.update(profileDTO.getId(), convertToUserProfile(profileDTO));

        return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
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
