package com.fadlan.tht.repository;

import com.fadlan.tht.dto.BannerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BannerRepository {

    private final JdbcTemplate jdbcTemplate;

    public BannerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<BannerDto> bannerRowMapper = (rs, rowNum) -> new BannerDto(
            rs.getString("banner_name"),
            rs.getString("banner_image"),
            rs.getString("description"));

    public List<BannerDto> findAll() {
        String sql = "SELECT banner_name, banner_image, description FROM banners ORDER BY id ASC";
        return jdbcTemplate.query(sql, bannerRowMapper);
    }
}
