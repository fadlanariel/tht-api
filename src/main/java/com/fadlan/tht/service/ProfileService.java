package com.fadlan.tht.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface ProfileService {
    Map<String, Object> getProfile(String email);

    Map<String, Object> updateProfile(String email, String firstName, String lastName);

    Map<String, Object> uploadProfileImage(String email, MultipartFile file) throws Exception;
}
