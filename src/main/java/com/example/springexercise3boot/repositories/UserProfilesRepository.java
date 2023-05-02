package com.example.springexercise3boot.repositories;

import com.example.springexercise3boot.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> queryDistinctByUsername(String username);

    Optional<UserProfile> queryDistinctByEmail(String email);
}
