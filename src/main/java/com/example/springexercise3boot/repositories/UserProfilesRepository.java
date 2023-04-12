package com.example.springexercise3boot.repositories;

import com.example.springexercise3boot.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile queryDistinctByUsername(String username);
}
