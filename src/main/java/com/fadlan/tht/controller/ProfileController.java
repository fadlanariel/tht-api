package com.fadlan.tht.controller;

import com.fadlan.tht.dto.request.ProfileUpdateRequest;
import com.fadlan.tht.dto.response.ProfileResponse;
import com.fadlan.tht.security.AuthenticatedUser;
import com.fadlan.tht.security.SecurityUtils;
import com.fadlan.tht.service.ProfileService;
import com.fadlan.tht.util.JwtTokenUtil;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/profile")
@Tag(name = "Membership", description = "Module untuk registrasi, login, dan profile user")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    @Operation(summary = "Get Profile", description = "API Profile Private (memerlukan Token)")
    public ResponseEntity<Map<String, Object>> getProfile() {
        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();

        ProfileResponse profile = profileService.getProfile(currentUser.getEmail());
        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Sukses",
                "data", profile));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Profile", description = "Update first_name dan last_name")
    public ResponseEntity<Map<String, Object>> updateProfile(
            @RequestBody ProfileUpdateRequest request) {
        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();
        ProfileResponse profile = profileService.updateProfile(currentUser.getEmail(), request);
        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Update Profile berhasil",
                "data", profile));
    }

    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload Profile Image", description = "Upload/Update profile image")
    public ResponseEntity<Map<String, Object>> uploadProfileImage(
            @Parameter(description = "Pilih file image (jpeg/png)") 
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();
        String email = currentUser.getEmail();

        // Validasi file type
        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", 102,
                "message", "Format Image tidak sesuai",
                "data", null
            ));
        }

        // Simulasi simpan file → ganti sesuai implementasi storage
        String imageUrl = "https://yoururlapi.com/profile-" + System.currentTimeMillis() + ".jpeg";

        ProfileResponse profile = profileService.updateProfileImage(email, imageUrl);
        return ResponseEntity.ok(Map.of(
            "status", 0,
            "message", "Update Profile Image berhasil",
            "data", profile
        ));
    }

}
