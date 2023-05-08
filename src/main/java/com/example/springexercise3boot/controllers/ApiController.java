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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Slf4j
public class ApiController {
    private final UserProfileService userProfileService;
    private final UserProfileValidator userProfileValidator;

    @Autowired
    private ApiController(UserProfileService userProfileService, UserProfileValidator userProfileValidator) {
        this.userProfileService = userProfileService;
        this.userProfileValidator = userProfileValidator;
    }

    @GetMapping("users")
    public List<UserProfile> getUsers() {
        log.info("API: requesting list of UserProfiles");

        return userProfileService.findAll();
    }

    @PostMapping(value = "users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addUser(@RequestBody @Valid UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {
        log.info("API: Attempt inserting user into database. Login: " + profileDTO.getUsername()
                + " Role: " + profileDTO.getRole()
                + " Name: " + profileDTO.getName()
                + " Email: " + profileDTO.getEmail());

        userProfileValidator.validate(profileDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("API: User can't be added because of invalid data");
            throw new BindException(bindingResult);
        }

        userProfileService.save(convertToUserProfile(profileDTO));
        log.info("API: User successfully created");

        return new ResponseEntity<>("API: User successfully created", HttpStatus.CREATED);
    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<String> deleteUserById(@RequestParam("id") int id) {
        log.info("API: Attempt removing user from database, ID: " + id);

        if (userProfileService.findOne(id) != null) {
            userProfileService.delete(id);
        }
        log.info("API: User with id " + id + " successfully deleted");
        return new ResponseEntity<>("API: User successfully deleted", HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public UserProfile getUserProfileById(@PathVariable("id") int id) {
        log.info("API: UserProfile with ID: " + id + " requested from DB");

        return userProfileService.findOne(id);
    }

    @GetMapping("user/{username}")
    public UserProfile getUserProfileByUsername(@PathVariable("username") String username) {
        log.info("API: UserProfile with username " + username + " requested from DB");

        return userProfileService.findByUsername(username);
    }

    @PostMapping("updateUser")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {
        log.info("API: Attempt to update user in database. Name: " + profileDTO.getName() + " Email: " + profileDTO.getEmail());

        userProfileValidator.validate(profileDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("API: User can't be updated because of invalid data");
            throw new BindException(bindingResult);
        }

        log.info("API: User successfully updated");
        userProfileService.update(profileDTO.getId(), convertToUserProfile(profileDTO));

        return new ResponseEntity<>("API: User successfully updated", HttpStatus.OK);
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
