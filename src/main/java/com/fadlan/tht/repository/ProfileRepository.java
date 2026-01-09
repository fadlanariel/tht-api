package com.fadlan.tht.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fadlan.tht.dto.response.ProfileResponse;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ProfileResponse getProfileByEmail(String email) {
        String sql = """
                    SELECT u.email, COALESCE(u.first_name,'') AS first_name,
                           COALESCE(u.last_name,'') AS last_name,
                           COALESCE(p.profile_image,'') AS profile_image
                    FROM users u
                    LEFT JOIN profile_images p ON u.id = p.user_id
                    WHERE u.email = ?
                """;
        return jdbcTemplate.queryForObject(sql, new Object[] { email }, (rs, rowNum) -> {
            ProfileResponse profile = new ProfileResponse();
            profile.setEmail(rs.getString("email"));
            profile.setFirstName(rs.getString("first_name"));
            profile.setLastName(rs.getString("last_name"));
            profile.setProfileImage(rs.getString("profile_image"));
            return profile;
        });
    }

    public int updateProfile(String email, String firstName, String lastName) {
        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE email = ?";
        return jdbcTemplate.update(sql, firstName, lastName, email);
    }

    public int updateProfileImage(Long userId, String imageUrl) {
        String sql = """
                    INSERT INTO profile_images(user_id, profile_image)
                    VALUES (?, ?)
                    ON CONFLICT (user_id) DO UPDATE
                    SET profile_image = EXCLUDED.profile_image,
                        updated_at = CURRENT_TIMESTAMP
                """;
        return jdbcTemplate.update(sql, userId, imageUrl);
    }

    public Long getUserIdByEmail(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, email);
    }
}
