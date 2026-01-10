package com.fadlan.tht.repository;

import com.fadlan.tht.dto.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ServiceRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ServiceDto> findAllServices() {
        String sql = "SELECT service_code, service_name, service_icon, service_tariff FROM services";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ServiceDto(
                        rs.getString("service_code"),
                        rs.getString("service_name"),
                        rs.getString("service_icon"),
                        rs.getLong("service_tariff")));
    }

    public Optional<ServiceDto> findByCode(String serviceCode) {
        String sql = "SELECT service_code, service_name, service_icon, service_tariff FROM services WHERE service_code = ?";
        List<ServiceDto> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ServiceDto(
                        rs.getString("service_code"),
                        rs.getString("service_name"),
                        rs.getString("service_icon"),
                        rs.getLong("service_tariff")),
                serviceCode);
        return result.stream().findFirst();
    }
}
