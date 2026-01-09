package com.fadlan.tht.controller;

import com.fadlan.tht.dto.request.ProfileUpdateRequest;
import com.fadlan.tht.service.ProfileService;
import com.fadlan.tht.util.JwtTokenUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
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
    private final JwtTokenUtil jwtTokenUtil;

    public ProfileController(ProfileService profileService, JwtTokenUtil jwtTokenUtil) {
        this.profileService = profileService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String email = jwtTokenUtil.getEmailFromToken(authHeader);
        Map<String, Object> data = profileService.getProfile(email);
        return ResponseEntity.ok(Map.of("status", 0, "message", "Sukses", "data", data));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProfileUpdateRequest request) {

        String email = jwtTokenUtil.getEmailFromToken(authHeader);
        Map<String, Object> data = profileService.updateProfile(email, request.getFirst_name(), request.getLast_name());
        return ResponseEntity.ok(Map.of("status", 0, "message", "Update profile berhasil", "data", data));
    }

    @PutMapping(value = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updateProfileImage(
            @RequestPart("image") MultipartFile imageFile,
            @RequestHeader("Authorization") String authHeader) {

        // Ambil token dari header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Missing or invalid Authorization header", "imageUrl", ""));
        }

        String token = authHeader.substring(7); // buang "Bearer "
        String email;
        try {
            email = jwtTokenUtil.getEmailFromToken(token); // pakai JwtTokenUtil kamu
            if (email == null)
                throw new RuntimeException("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid token", "imageUrl", ""));
        }

        // Validasi file
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "No file uploaded", "imageUrl", ""));
        }

        try {
            // Simulasi upload ke folder lokal "uploads/"
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists())
                dir.mkdirs();

            String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(dir, filename);
            imageFile.transferTo(dest);

            String uploadedFileUrl = "/uploads/" + filename; // bisa disesuaikan dengan endpoint statis

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Upload berhasil");
            response.put("imageUrl", uploadedFileUrl);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Upload gagal: " + e.getMessage(), "imageUrl", ""));
        }
    }

}
