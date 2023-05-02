package com.example.springexercise3boot.services;

import com.example.springexercise3boot.models.UserProfile;
import com.example.springexercise3boot.repositories.UserProfilesRepository;
import com.example.springexercise3boot.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserProfileService {

    private final UserProfilesRepository userProfilesRepository;

    @Autowired
    public UserProfileService(UserProfilesRepository userProfilesRepository) {
        this.userProfilesRepository = userProfilesRepository;
    }

    public List<UserProfile> findAll() {
        return userProfilesRepository.findAll();
    }

    public UserProfile findOne(int id) {
        Optional<UserProfile> foundUserProfile = userProfilesRepository.findById(id);
        return foundUserProfile.orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
    }

    public UserProfile findByUsername(String username) {
        Optional<UserProfile> foundUserProfile = userProfilesRepository.queryDistinctByUsername(username);
        return foundUserProfile.orElse(null);
    }

    public UserProfile findByEmail(String email) {
        Optional<UserProfile> foundUserProfile = userProfilesRepository.queryDistinctByEmail(email);
        return foundUserProfile.orElse(null);
    }

    @Transactional
    public void save(UserProfile profile) {
        userProfilesRepository.save(profile);
    }

    @Transactional
    public void update(int id, UserProfile updatedProfile) {
        updatedProfile.setId(id);
        userProfilesRepository.save(updatedProfile);
    }

    @Transactional
    public void delete(int id) {
        userProfilesRepository.deleteById(id);
    }
}
