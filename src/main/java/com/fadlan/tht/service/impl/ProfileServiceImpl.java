package com.fadlan.tht.service.impl;

import com.fadlan.tht.service.ProfileService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final JdbcTemplate jdbcTemplate;

    public ProfileServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, Object> getProfile(String email) {
        String sql = "SELECT email, COALESCE(first_name, '') AS first_name, COALESCE(last_name, '') AS last_name, COALESCE(profile_image, '') AS profile_image FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { email }, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("email", rs.getString("email"));
            map.put("first_name", rs.getString("first_name"));
            map.put("last_name", rs.getString("last_name"));
            map.put("profile_image", rs.getString("profile_image"));
            return map;
        });
    }

    @Override
    public Map<String, Object> updateProfile(String email, String firstName, String lastName) {
        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE email = ?";
        jdbcTemplate.update(sql, firstName, lastName, email);
        return getProfile(email);
    }

    @Override
    public Map<String, Object> uploadProfileImage(String email, MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            throw new IllegalArgumentException("Format image tidak sesuai (harus jpeg/png)");
        }

        // Save image ke folder lokal
        String folder = "uploads/";
        Files.createDirectories(Paths.get(folder));
        String filename = email.replaceAll("[^a-zA-Z0-9]", "_") + "_" + System.currentTimeMillis()
                + (contentType.equals("image/png") ? ".png" : ".jpeg");
        String filepath = folder + filename;
        file.transferTo(new File(filepath));

        String imageUrl = "https://yoururlapi.com/" + filename; // ganti sesuai URL deploy
        String sql = "UPDATE users SET profile_image = ? WHERE email = ?";
        jdbcTemplate.update(sql, imageUrl, email);

        return getProfile(email);
    }
}
