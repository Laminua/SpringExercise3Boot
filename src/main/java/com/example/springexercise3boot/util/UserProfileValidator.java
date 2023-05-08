package com.example.springexercise3boot.util;

import com.example.springexercise3boot.dto.UserProfileDTO;
import com.example.springexercise3boot.models.UserProfile;
import com.example.springexercise3boot.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserProfileValidator implements Validator {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileValidator(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserProfileDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserProfileDTO userProfileDTO = (UserProfileDTO) target;

        if (userProfileDTO.getId() == 0) {
            if (userProfileService.findByUsername(userProfileDTO.getUsername()) != null) {
                errors.rejectValue("username", "400", "Username already exists");
            }

            if (userProfileService.findByEmail(userProfileDTO.getEmail()) != null) {
                errors.rejectValue("email", "400", "Email already taken");
            }
        } else {
            boolean usernameNeedValidation = false;
            boolean emailNeedValidation = false;
            UserProfile userProfile = userProfileService.findOne(userProfileDTO.getId());

            if (!userProfile.getUsername().equals(userProfileDTO.getUsername())) {
                usernameNeedValidation = true;
            }

            if (!userProfile.getEmail().equals(userProfileDTO.getEmail())) {
                emailNeedValidation = true;
            }

            if (usernameNeedValidation && (userProfileService.findByUsername(userProfileDTO.getUsername()) != null)) {
                errors.rejectValue("username", "400", "Username already exists");
            }

            if (emailNeedValidation && (userProfileService.findByEmail(userProfileDTO.getEmail()) != null)) {
                errors.rejectValue("email", "400", "Email already taken");
            }
        }
    }
}
