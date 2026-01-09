package com.fadlan.tht.service;

import org.springframework.stereotype.Service;

import com.fadlan.tht.dto.request.ProfileUpdateRequest;
import com.fadlan.tht.dto.response.ProfileResponse;
import com.fadlan.tht.repository.ProfileRepository;

import java.util.Map;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileResponse getProfile(String email) {
        return profileRepository.getProfileByEmail(email);
    }

    public ProfileResponse updateProfile(String email, ProfileUpdateRequest request) {
        profileRepository.updateProfile(email, request.getFirstName(), request.getLastName());
        return getProfile(email);
    }

    public ProfileResponse updateProfileImage(String email, String imageUrl) {
        Long userId = profileRepository.getUserIdByEmail(email);
        profileRepository.updateProfileImage(userId, imageUrl);
        return getProfile(email);
    }
}
